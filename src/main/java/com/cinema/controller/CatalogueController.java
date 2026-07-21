package com.cinema.controller;

import com.cinema.dao.FilmDAO;
import com.cinema.dao.ReservationDAO;
import com.cinema.dao.SeanceDAO;
import com.cinema.model.Client;
import com.cinema.model.Film;
import com.cinema.model.Reservation;
import com.cinema.model.Seance;
import com.cinema.view.CatalogueView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import java.util.List;
import java.util.Optional;

public class CatalogueController {
    private CatalogueView view;
    private FilmDAO filmDAO;
    private SeanceDAO seanceDAO;
    private ReservationDAO reservationDAO;
    private Client clientConnecte;

    public CatalogueController(CatalogueView view, Client client) {
        this.view = view;
        this.clientConnecte = client;
        this.filmDAO = new FilmDAO();
        this.seanceDAO = new SeanceDAO();
        this.reservationDAO = new ReservationDAO();

        loadFilms();
    }

    private void loadFilms() {
        List<Film> films = filmDAO.listerFilms();
        view.getGrid().getChildren().clear();

        for (Film film : films) {
            VBox card = createFilmCard(film);
            view.getGrid().getChildren().add(card);
        }
    }

    private VBox createFilmCard(Film film) {
        VBox card = new VBox(10);
        card.getStyleClass().add("film-card");
        card.setAlignment(Pos.CENTER);

        Label title = new Label(film.getTitre());
        title.getStyleClass().add("film-title");

        Label genre = new Label("🎭 " + film.getGenre());
        genre.getStyleClass().add("film-info");

        Label duree = new Label("⏱ " + film.getDureeMinutes() + " min");
        duree.getStyleClass().add("film-info");

        Label realisateur = new Label("🎬 " + film.getRealisateur());
        realisateur.getStyleClass().add("film-info");

        Button btnReserver = new Button("Voir les séances");
        btnReserver.getStyleClass().add("btn-reserver");
        
        btnReserver.setOnAction(e -> handleReservation(film));

        card.getChildren().addAll(title, genre, duree, realisateur, btnReserver);
        return card;
    }

    private void handleReservation(Film film) {
        // On récupère les séances pour ce film précis
        List<Seance> seancesDisponibles = seanceDAO.listerSeancesParFilm(film.getIdFilm());

        if (seancesDisponibles.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Désolé", "Aucune séance n'est programmée pour ce film actuellement.");
            return;
        }

        Dialog<Reservation> dialog = new Dialog<>();
        dialog.setTitle("Réserver un ticket");
        DialogPane dialogPane = dialog.getDialogPane();
        
        try { dialogPane.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm()); } catch (Exception e) {}
        dialogPane.getStyleClass().add("form-card");

        ButtonType reserveBtnType = new ButtonType("Confirmer l'achat", ButtonBar.ButtonData.OK_DONE);
        dialogPane.getButtonTypes().addAll(reserveBtnType, ButtonType.CANCEL);
        
        Button reserveBtn = (Button) dialogPane.lookupButton(reserveBtnType);
        reserveBtn.getStyleClass().add("login-button");
        
        Button cancelBtn = (Button) dialogPane.lookupButton(ButtonType.CANCEL);
        cancelBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #a4b0be; -fx-cursor: hand; -fx-font-weight: bold;");

        VBox vbox = new VBox(15);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20, 30, 20, 30));

        Label titleLabel = new Label("Réserver : " + film.getTitre());
        titleLabel.getStyleClass().add("titre-login");

        ComboBox<Seance> seanceBox = new ComboBox<>();
        seanceBox.getItems().addAll(seancesDisponibles);
        seanceBox.setPromptText("Choisissez une date et heure");
        seanceBox.setMaxWidth(Double.MAX_VALUE);
        seanceBox.getStyleClass().add("input-field");
        
        seanceBox.setConverter(new StringConverter<Seance>() {
            @Override
            public String toString(Seance s) {
                if (s == null) return "";
                return s.getDateProjection() + " à " + s.getHeureDebut() + " - " + s.getNomSalle() + " (" + s.getTarif() + " MAD)";
            }
            @Override public Seance fromString(String string) { return null; }
        });

        Label placesLabel = new Label("Nombre de places :");
        placesLabel.setStyle("-fx-text-fill: #7f8c8d; -fx-font-weight: bold;");
        Spinner<Integer> placesSpinner = new Spinner<>(1, 10, 1);
        placesSpinner.getStyleClass().add("input-field");
        placesSpinner.setMaxWidth(Double.MAX_VALUE);

        vbox.getChildren().addAll(titleLabel, seanceBox, placesLabel, placesSpinner);
        dialogPane.setContent(vbox);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == reserveBtnType) {
                Seance seanceChoisie = seanceBox.getValue();
                if (seanceChoisie == null) {
                    showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez choisir une séance.");
                    return null;
                }
                int nbPlaces = placesSpinner.getValue();
                // On crée la réservation avec le statut "Confirmée"
                return new Reservation(clientConnecte.getIdClient(), seanceChoisie.getIdSeance(), nbPlaces, "Confirmée");
            }
            return null;
        });

        Optional<Reservation> result = dialog.showAndWait();
        result.ifPresent(nouvelleResa -> {
            if (reservationDAO.ajouterReservation(nouvelleResa)) {
                showAlert(Alert.AlertType.INFORMATION, "Félicitations 🎉", "Votre réservation a été confirmée ! Retrouvez-la dans 'Mes Tickets'.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Un problème est survenu lors de la réservation.");
            }
        });
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null); alert.setTitle(title); alert.setContentText(content); alert.showAndWait();
    }
}
