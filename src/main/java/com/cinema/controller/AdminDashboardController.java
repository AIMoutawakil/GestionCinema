package com.cinema.controller;

import com.cinema.view.AdminDashboardView;
import com.cinema.view.FilmManagementView;
import com.cinema.view.LoginView;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AdminDashboardController {
    private AdminDashboardView view;
    private Stage stage;

    public AdminDashboardController(AdminDashboardView view, Stage stage) {
        this.view = view;
        this.stage = stage;
    }

    public void loadBilan() {
        view.getContentArea().getChildren().clear();

        com.cinema.view.BilanView bilanView = new com.cinema.view.BilanView();
        new com.cinema.controller.BilanController(bilanView);

        view.getContentArea().getChildren().add(bilanView.getView());
    }
    
    public void loadFilmManagement() {

    	view.getContentArea().getChildren().clear();
        com.cinema.view.FilmManagementView filmView = new com.cinema.view.FilmManagementView();
        new com.cinema.controller.FilmManagementController(filmView);
        view.getContentArea().getChildren().add(filmView.getView());
    }
    
    public void loadSalleManagement() {
        view.getContentArea().getChildren().clear();
        
        com.cinema.view.SalleManagementView salleView = new com.cinema.view.SalleManagementView();
        new com.cinema.controller.SalleManagementController(salleView); 
        
        view.getContentArea().getChildren().add(salleView.getView());
    }
    
    public void loadSeanceManagement() {
        view.getContentArea().getChildren().clear();
        com.cinema.view.SeanceManagementView seanceView = new com.cinema.view.SeanceManagementView();
        new com.cinema.controller.SeanceManagementController(seanceView); 
        view.getContentArea().getChildren().add(seanceView.getView());
    }

    public void loadReservationManagement() {
        view.getContentArea().getChildren().clear();
        
        com.cinema.view.ReservationManagementView resView = new com.cinema.view.ReservationManagementView();
        new com.cinema.controller.ReservationManagementController(resView);
        
        view.getContentArea().getChildren().add(resView.getView());
    }
    public void handleLogout() {
        LoginView loginView = new LoginView(stage);
        stage.getScene().setRoot(loginView.getView());
    }
}