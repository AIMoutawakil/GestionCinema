package com.cinema.controller;

import com.cinema.dao.FilmDAO;
import com.cinema.dao.SalleDAO;
import com.cinema.dao.SeanceDAO;
import com.cinema.model.Film;
import com.cinema.model.Salle;
import com.cinema.model.Seance;
import com.cinema.view.SeanceManagementView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Optional;

public class SeanceManagementController {
    private SeanceManagementView view;
    private SeanceDAO seanceDAO;
    private FilmDAO filmDAO;
    private SalleDAO salleDAO;
    private ObservableList<Seance> masterData;

    public SeanceManagementController(SeanceManagementView view) {
        this.view = view;
        this.seanceDAO = new SeanceDAO();
        this.filmDAO = new FilmDAO();
        this.salleDAO = new SalleDAO();
        this.masterData = FXCollections.observableArrayList();

        loadData();
        setupSearchFilter();

        view.getBtnAdd().setOnAction(e -> handleAdd());
        view.getBtnEdit().setOnAction(e -> handleEdit());
        view.getBtnDelete().setOnAction(e -> handleDelete());
    }

    private void loadData() {
        masterData.setAll(seanceDAO.listerSeances());
    }

    private void setupSearchFilter() {
        FilteredList<Seance> filteredData = new FilteredList<>(masterData, p -> true);
        view.getSearchField().textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(seance -> {
                if (newValue == null || newValue.isEmpty()) return true;
                String filter = newValue.toLowerCase();
                return seance.getTitreFilm().toLowerCase().contains(filter) ||
                       seance.getNomSalle().toLowerCase().contains(filter);
            });
        });

        SortedList<Seance> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(view.getTable().comparatorProperty());
        view.getTable().setItems(sortedData);
    }

    private void handleAdd() {
        Optional<Seance> result = showSeanceFormDialog(null);
        result.ifPresent(nouvelleSeance -> {
            if (seanceDAO.ajouterSeance(nouvelleSeance)) {
                loadData();
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "L'ajout a échoué.");
            }
        });
    }

    private void handleEdit() {
        Seance selectedSeance = view.getTable().getSelectionModel().getSelectedItem();
        if (selectedSeance != null) {
            Optional<Seance> result = showSeanceFormDialog(selectedSeance);
            result.ifPresent(seanceModifiee -> {
                seanceModifiee.setIdSeance(selectedSeance.getIdSeance());
                if (seanceDAO.modifierSeance(seanceModifiee)) {
                    loadData();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "La modification a échoué.");
                }
            });
        }
    }

    private void handleDelete() {
        Seance selectedSeance = view.getTable().getSelectionModel().getSelectedItem();
        if (selectedSeance != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setHeaderText(null);
            confirm.setContentText("Supprimer la séance du film " + selectedSeance.getTitreFilm() + " ?");

            if (confirm.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
                seanceDAO.supprimerSeance(selectedSeance.getIdSeance());
                loadData();
            }
        }
    }

    private Optional<Seance> showSeanceFormDialog(Seance seanceToEdit) {
        Dialog<Seance> dialog = new Dialog<>();
        dialog.setTitle(seanceToEdit == null ? "Ajouter une séance" : "Modifier la séance");
        DialogPane dialogPane = dialog.getDialogPane();

        try {
            dialogPane.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        } catch (Exception e) {}

        dialogPane.getStyleClass().add("form-card");

        ButtonType saveBtnType = new ButtonType("Sauvegarder", ButtonBar.ButtonData.OK_DONE);
        dialogPane.getButtonTypes().addAll(saveBtnType, ButtonType.CANCEL);

        ((Button) dialogPane.lookupButton(saveBtnType)).getStyleClass().add("login-button");
        ((Button) dialogPane.lookupButton(ButtonType.CANCEL)).setStyle("-fx-background-color: transparent; -fx-text-fill: #a4b0be; -fx-cursor: hand; -fx-font-weight: bold;");

        VBox vbox = new VBox(15);
        vbox.setAlignment(javafx.geometry.Pos.CENTER);
        vbox.setPadding(new Insets(20, 30, 20, 30));

        Label titleLabel = new Label(seanceToEdit == null ? "Nouvelle Séance" : "Mise à jour");
        titleLabel.getStyleClass().add("titre-login");

        ComboBox<Film> filmBox = new ComboBox<>();
        filmBox.getItems().addAll(filmDAO.listerFilms());
        filmBox.setPromptText("Sélectionnez un film");
        filmBox.setMaxWidth(Double.MAX_VALUE);
        filmBox.getStyleClass().add("input-field");
        filmBox.setConverter(new StringConverter<Film>() {
            @Override
            public String toString(Film film) {
                return film != null ? film.getTitre() : "";
            }

            @Override
            public Film fromString(String string) {
                return null;
            }
        });

        ComboBox<Salle> salleBox = new ComboBox<>();
        salleBox.getItems().addAll(salleDAO.listerSalles());
        salleBox.setPromptText("Sélectionnez une salle");
        salleBox.setMaxWidth(Double.MAX_VALUE);
        salleBox.getStyleClass().add("input-field");
        salleBox.setConverter(new StringConverter<Salle>() {
            @Override
            public String toString(Salle salle) {
                return salle != null ? salle.getNomSalle() + " (" + salle.getTechnologie() + ")" : "";
            }

            @Override
            public Salle fromString(String string) {
                return null;
            }
        });

        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Date de la projection");
        datePicker.setMaxWidth(Double.MAX_VALUE);
        datePicker.setStyle("-fx-font-size: 14px;");

        TextField heureField = new TextField();
        heureField.setPromptText("Heure (ex: 14:30)");
        heureField.getStyleClass().add("input-field");

        TextField tarifField = new TextField();
        tarifField.setPromptText("Tarif en MAD (ex: 60.50)");
        tarifField.getStyleClass().add("input-field");

        if (seanceToEdit != null) {
            filmBox.getItems().stream().filter(f -> f.getIdFilm() == seanceToEdit.getIdFilm()).findFirst().ifPresent(filmBox::setValue);
            salleBox.getItems().stream().filter(s -> s.getIdSalle() == seanceToEdit.getIdSalle()).findFirst().ifPresent(salleBox::setValue);
            datePicker.setValue(seanceToEdit.getDateProjection());
            heureField.setText(seanceToEdit.getHeureDebut().toString());
            tarifField.setText(String.valueOf(seanceToEdit.getTarif()));
        }

        vbox.getChildren().addAll(titleLabel, filmBox, salleBox, datePicker, heureField, tarifField);
        dialogPane.setContent(vbox);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveBtnType) {
                try {
                    Film filmSelectionne = filmBox.getValue();
                    Salle salleSelectionnee = salleBox.getValue();
                    LocalDate date = datePicker.getValue();
                    LocalTime heure = LocalTime.parse(heureField.getText());
                    double tarif = Double.parseDouble(tarifField.getText());

                    if (filmSelectionne == null || salleSelectionnee == null || date == null) {
                        showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez remplir tous les champs correctement.");
                        return null;
                    }

                    return new Seance(filmSelectionne.getIdFilm(), salleSelectionnee.getIdSalle(), date, heure, tarif);
                } catch (DateTimeParseException e) {
                    showAlert(Alert.AlertType.ERROR, "Erreur de format", "L'heure doit être au format HH:MM (ex: 14:30).");
                } catch (NumberFormatException e) {
                    showAlert(Alert.AlertType.ERROR, "Erreur de format", "Le tarif doit être un nombre valide.");
                }
            }
            return null;
        });

        return dialog.showAndWait();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
