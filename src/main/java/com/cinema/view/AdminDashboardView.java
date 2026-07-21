package com.cinema.view;

import com.cinema.controller.AdminDashboardController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class AdminDashboardView {
    private BorderPane root;
    private VBox sidebar;
    private StackPane contentArea; 
    private AdminDashboardController controller;

    public AdminDashboardView(Stage stage) {
        root = new BorderPane();
        root.getStyleClass().add("dashboard-root"); 

        
        sidebar = new VBox(15);
        sidebar.getStyleClass().add("sidebar");
        sidebar.setPrefWidth(280); 
        sidebar.setPadding(new Insets(30, 20, 30, 20));

        
        Label brandLabel = new Label("CINÉMA ADMIN");
        brandLabel.getStyleClass().add("sidebar-brand");


        Button btnBilan = createNavButton("📊 Tableau de Bord");
        Button btnFilms = createNavButton("Gestion des Films");
        Button btnSalles = createNavButton("Gestion des Salles");
        Button btnSeances = createNavButton("Planning Séances");
        Button btnReservations = createNavButton("Réservations");
        

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);


        Button btnLogout = new Button("Déconnexion");
        btnLogout.getStyleClass().add("btn-logout");
        
        
        javafx.scene.shape.SVGPath logoutIcon = new javafx.scene.shape.SVGPath();
   
        logoutIcon.setContent("M10 22H5C4.44772 22 4 21.5523 4 21V3C4 2.44772 4.44772 2 5 2H10C10.5523 2 11 2.44772 11 3C11 3.55228 10.5523 4 10 4H6V20H10C10.5523 20 11 20.4477 11 21C11 21.5523 10.5523 22 10 22ZM15.0355 16.0355L20.0711 11L15.0355 5.96447L13.6213 7.37868L16.2426 10H8V12H16.2426L13.6213 14.6213L15.0355 16.0355Z");
        logoutIcon.setFill(javafx.scene.paint.Color.WHITE); 
        
        
        btnLogout.setGraphic(logoutIcon);
        btnLogout.setGraphicTextGap(15); 

        sidebar.getChildren().addAll(brandLabel, btnBilan, btnFilms, btnSalles, btnSeances, btnReservations, spacer, btnLogout);

        contentArea = new StackPane();
        contentArea.getStyleClass().add("work-area"); 
        

        showWelcomeMessage();


        root.setLeft(sidebar);
        root.setCenter(contentArea);

  
        controller = new AdminDashboardController(this, stage);
        
        btnBilan.setOnAction(e -> controller.loadBilan());
        btnFilms.setOnAction(e -> controller.loadFilmManagement());
        btnSalles.setOnAction(e -> controller.loadSalleManagement());
        btnSeances.setOnAction(e -> controller.loadSeanceManagement());
        btnReservations.setOnAction(e -> controller.loadReservationManagement());
        btnLogout.setOnAction(e -> controller.handleLogout());
    }

    private Button createNavButton(String text) {
        Button btn = new Button(text);
        btn.getStyleClass().add("nav-button");
        btn.setMaxWidth(Double.MAX_VALUE);
        return btn;
    }

    public void showWelcomeMessage() {
        contentArea.getChildren().clear();
        VBox welcomeBox = new VBox(10);
        welcomeBox.setAlignment(Pos.CENTER);
        
        Label title = new Label("Bienvenue dans votre Espace de Gestion");
        title.getStyleClass().add("titre-welcome"); 
        
        Label sub = new Label("Sélectionnez une option à gauche pour commencer.");
        sub.setStyle("-fx-text-fill: #a4b0be; -fx-font-size: 14px;");
        
        welcomeBox.getChildren().addAll(title, sub);
        contentArea.getChildren().add(welcomeBox);
    }

    public BorderPane getView() { return root; }
    public StackPane getContentArea() { return contentArea; }
}
