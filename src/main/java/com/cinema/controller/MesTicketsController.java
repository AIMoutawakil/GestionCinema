package com.cinema.controller;

import com.cinema.dao.ReservationDAO;
import com.cinema.model.Client;
import com.cinema.model.Reservation;
import com.cinema.view.MesTicketsView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class MesTicketsController {
    private MesTicketsView view;
    private ReservationDAO reservationDAO;
    private Client clientConnecte;
    private ObservableList<Reservation> mesDonnees;

    public MesTicketsController(MesTicketsView view, Client client) {
        this.view = view;
        this.clientConnecte = client;
        this.reservationDAO = new ReservationDAO();
        this.mesDonnees = FXCollections.observableArrayList();

        loadData();

        view.getBtnCancel().setOnAction(e -> handleCancel());
    }

    private void loadData() {
        mesDonnees.setAll(reservationDAO.listerMesTickets(clientConnecte.getIdClient()));
        view.getTable().setItems(mesDonnees);
    }

    private void handleCancel() {
        Reservation selected = view.getTable().getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setHeaderText(null);
            confirm.setContentText("Voulez-vous vraiment annuler votre réservation pour " + selected.getTitreFilm() + " ?");

            if (confirm.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
                if (reservationDAO.supprimerReservation(selected.getIdReservation())) {
                    loadData();
                    showAlert(Alert.AlertType.INFORMATION, "Succès", "Votre réservation a été annulée.");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible d'annuler cette réservation.");
                }
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez sélectionner un billet à annuler.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
