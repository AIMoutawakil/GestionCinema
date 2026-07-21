package com.cinema.controller;

import com.cinema.dao.ReservationDAO;
import com.cinema.model.Reservation;
import com.cinema.view.ReservationManagementView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class ReservationManagementController {
    private ReservationManagementView view;
    private ReservationDAO reservationDAO;
    private ObservableList<Reservation> masterData;

    public ReservationManagementController(ReservationManagementView view) {
        this.view = view;
        this.reservationDAO = new ReservationDAO();
        this.masterData = FXCollections.observableArrayList();

        loadData();
        setupSearchFilter();

        view.getBtnCancel().setOnAction(e -> handleCancel());
    }

    private void loadData() {
        masterData.setAll(reservationDAO.listerReservations());
    }

    private void setupSearchFilter() {
        FilteredList<Reservation> filteredData = new FilteredList<>(masterData, p -> true);
        view.getSearchField().textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(res -> {
                if (newValue == null || newValue.isEmpty()) return true;
                String filter = newValue.toLowerCase();
                return res.getNomClient().toLowerCase().contains(filter) || 
                       res.getTitreFilm().toLowerCase().contains(filter);
            });
        });
        SortedList<Reservation> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(view.getTable().comparatorProperty());
        view.getTable().setItems(sortedData);
    }

    private void handleCancel() {
        Reservation selected = view.getTable().getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setHeaderText(null);
            confirm.setContentText("Voulez-vous vraiment annuler la réservation de " + selected.getNomClient() + " ?");
            if (confirm.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
                if(reservationDAO.supprimerReservation(selected.getIdReservation())) {
                    loadData();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible d'annuler la réservation.");
                }
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez sélectionner une réservation.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null); alert.setTitle(title); alert.setContentText(content); alert.showAndWait();
    }
}