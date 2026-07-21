package com.cinema.controller;

import com.cinema.dao.FilmDAO;
import com.cinema.model.Film;
import com.cinema.view.FilmManagementView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.Optional;

public class FilmManagementController {
    private FilmManagementView view;
    private FilmDAO filmDAO;
    private ObservableList<Film> masterData; // La liste contenant tous les films

    public FilmManagementController(FilmManagementView view) {
        this.view = view;
        this.filmDAO = new FilmDAO();
        this.masterData = FXCollections.observableArrayList();

        // 1. Charger les données au démarrage
        loadData();

        // 2. Configurer la barre de recherche
        setupSearchFilter();

        // 3. Assigner les actions aux boutons
        view.getBtnAdd().setOnAction(e -> handleAdd());
        view.getBtnEdit().setOnAction(e -> handleEdit());
        view.getBtnDelete().setOnAction(e -> handleDelete());
    }

    // --- RECHARGER LA LISTE DEPUIS POSTGRESQL ---
    private void loadData() {
        masterData.setAll(filmDAO.listerFilms());
    }

    // --- BARRE DE RECHERCHE DYNAMIQUE ---
    private void setupSearchFilter() {
        // Crée une liste filtrée basée sur la masterData
        FilteredList<Film> filteredData = new FilteredList<>(masterData, p -> true);

        // Ajoute un écouteur sur le champ de recherche
        view.getSearchField().textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(film -> {
                // Si le texte est vide, on affiche tout
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                
                String lowerCaseFilter = newValue.toLowerCase();
                
                // On cherche dans le titre, le genre et le réalisateur
                if (film.getTitre().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (film.getGenre().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (film.getRealisateur() != null && film.getRealisateur().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false; 
            });
        });

        // On lie la liste filtrée au tableau
        SortedList<Film> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(view.getTable().comparatorProperty());
        view.getTable().setItems(sortedData);
    }

    // --- AJOUTER UN FILM ---
    private void handleAdd() {
        Optional<Film> result = showFilmFormDialog(null);
        result.ifPresent(nouveauFilm -> {
            if (filmDAO.ajouterFilm(nouveauFilm)) {
                loadData(); // On rafraîchit le tableau
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Le film a été ajouté.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "L'ajout a échoué.");
            }
        });
    }

    // --- MODIFIER UN FILM ---
    private void handleEdit() {
        Film selectedFilm = view.getTable().getSelectionModel().getSelectedItem();
        if (selectedFilm != null) {
            Optional<Film> result = showFilmFormDialog(selectedFilm);
            result.ifPresent(filmModifie -> {
                // On conserve l'ID original pour la mise à jour
                filmModifie.setIdFilm(selectedFilm.getIdFilm()); 
                if (filmDAO.modifierFilm(filmModifie)) {
                    loadData();
                    showAlert(Alert.AlertType.INFORMATION, "Succès", "Le film a été modifié.");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "La modification a échoué.");
                }
            });
        } else {
            showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez sélectionner un film à modifier.");
        }
    }

    // --- SUPPRIMER UN FILM ---
    private void handleDelete() {
        Film selectedFilm = view.getTable().getSelectionModel().getSelectedItem();
        if (selectedFilm != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirmation de suppression");
            confirm.setHeaderText(null);
            confirm.setContentText("Êtes-vous sûr de vouloir supprimer le film : " + selectedFilm.getTitre() + " ?");

            Optional<ButtonType> result = confirm.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (filmDAO.supprimerFilm(selectedFilm.getIdFilm())) {
                    loadData();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "La suppression a échoué.");
                }
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez sélectionner un film à supprimer.");
        }
    }

    // --- BOÎTE DE DIALOGUE (FORMULAIRE) POUR AJOUT ET MODIFICATION ---
 // --- BOÎTE DE DIALOGUE (FORMULAIRE) POUR AJOUT ET MODIFICATION ---
 // --- BOÎTE DE DIALOGUE (FORMULAIRE) POUR AJOUT ET MODIFICATION ---
    private Optional<Film> showFilmFormDialog(Film filmToEdit) {
        Dialog<Film> dialog = new Dialog<>();
        dialog.setTitle(filmToEdit == null ? "Ajouter un film" : "Modifier le film");

        DialogPane dialogPane = dialog.getDialogPane();
        
        try {
            dialogPane.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        } catch (Exception e) {
            System.out.println("Attention: Fichier CSS introuvable pour le dialogue.");
        }
        
        dialogPane.getStyleClass().add("form-card"); 

        ButtonType saveButtonType = new ButtonType("Sauvegarder", ButtonBar.ButtonData.OK_DONE);
        dialogPane.getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        Button saveButton = (Button) dialogPane.lookupButton(saveButtonType);
        saveButton.getStyleClass().add("login-button");

        Button cancelButton = (Button) dialogPane.lookupButton(ButtonType.CANCEL);
        cancelButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #a4b0be; -fx-cursor: hand; -fx-font-weight: bold;");

        VBox vbox = new VBox(15);
        vbox.setAlignment(javafx.geometry.Pos.CENTER);
        vbox.setPadding(new Insets(20, 30, 20, 30));

        Label titleLabel = new Label(filmToEdit == null ? "Nouveau Film" : "Mise à jour");
        titleLabel.getStyleClass().add("titre-login");

        TextField titre = new TextField();
        titre.setPromptText("Titre du film");
        titre.getStyleClass().add("input-field");

        // --- NOUVEAUTÉ : LA LISTE DÉROULANTE POUR LE GENRE ---
        ComboBox<String> genreBox = new ComboBox<>();
        genreBox.getItems().addAll(
            "Action", "Adventure", "Animation", "Biography", "Comedy", 
            "Crime", "Documentary", "Drama", "Family", "Fantasy", 
            "Historical", "Horror", "Mystery", "Musical", "Romance", 
            "Science Fiction", "Sport", "Thriller", "War", "Western"
        );
        genreBox.setPromptText("Sélectionnez un genre");
        genreBox.getStyleClass().add("input-field");
        genreBox.setMaxWidth(Double.MAX_VALUE); // Pour qu'elle prenne toute la largeur comme les autres champs

        TextField duree = new TextField();
        duree.setPromptText("Durée (en minutes)");
        duree.getStyleClass().add("input-field");

        TextField realisateur = new TextField();
        realisateur.setPromptText("Réalisateur");
        realisateur.getStyleClass().add("input-field");

        // Pré-remplissage en cas de modification
        if (filmToEdit != null) {
            titre.setText(filmToEdit.getTitre());
            genreBox.setValue(filmToEdit.getGenre()); // On sélectionne la valeur existante dans la liste
            duree.setText(String.valueOf(filmToEdit.getDureeMinutes()));
            realisateur.setText(filmToEdit.getRealisateur());
        }

        // On remplace "genre" par "genreBox" dans l'ajout à la VBox
        vbox.getChildren().addAll(titleLabel, titre, genreBox, duree, realisateur);
        dialogPane.setContent(vbox);

        // Conversion du résultat
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                try {
                    int dureeInt = Integer.parseInt(duree.getText());
                    String genreSelectionne = genreBox.getValue();
                    
                    // Vérification que l'utilisateur a bien choisi un genre
                    if (genreSelectionne == null) {
                        genreSelectionne = "Non défini"; 
                    }

                    // On utilise genreBox.getValue() pour créer le film
                    return new Film(titre.getText(), genreSelectionne, dureeInt, realisateur.getText(), "", "");
                } catch (NumberFormatException e) {
                    showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "La durée doit être un nombre entier.");
                    return null;
                }
            }
            return null;
        });

        return dialog.showAndWait();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}