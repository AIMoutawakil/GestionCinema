package com.cinema.controller;

import com.cinema.model.Client;
import com.cinema.view.ClientDashboardView;
import com.cinema.view.LoginView;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ClientDashboardController {
    private ClientDashboardView view;
    private Stage stage;
    private Client clientConnecte;

    public ClientDashboardController(ClientDashboardView view, Stage stage, Client client) {
        this.view = view;
        this.stage = stage;
        this.clientConnecte = client;
    }

    public void loadCatalogue() {
        view.getContentArea().getChildren().clear();
        
        com.cinema.view.CatalogueView catalogueView = new com.cinema.view.CatalogueView();
        new com.cinema.controller.CatalogueController(catalogueView, clientConnecte);
        
        view.getContentArea().getChildren().add(catalogueView.getView());
    }

    public void loadMesReservations() {
        view.getContentArea().getChildren().clear();
        
        // On instancie la vue et le contrôleur en passant le client connecté
        com.cinema.view.MesTicketsView ticketsView = new com.cinema.view.MesTicketsView();
        new com.cinema.controller.MesTicketsController(ticketsView, clientConnecte);
        
        view.getContentArea().getChildren().add(ticketsView.getView());
    }
    public void handleLogout() {
        LoginView loginView = new LoginView(stage);
        stage.getScene().setRoot(loginView.getView());
        stage.setWidth(800); // Redimensionne à la taille de la page login
        stage.setHeight(600);
        stage.centerOnScreen();
    }
}