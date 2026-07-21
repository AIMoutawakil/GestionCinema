package com.cinema.view;

import com.cinema.controller.ClientDashboardController;
import com.cinema.model.Client;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ClientDashboardView {
    private BorderPane root;
    private VBox sidebar;
    private StackPane contentArea;
    private ClientDashboardController controller;
    private Client clientConnecte;

    public ClientDashboardView(Stage stage, Client client) {
        this.clientConnecte = client;
        root = new BorderPane();
        root.getStyleClass().add("dashboard-root"); // On réutilise le fond global

        // 1. SIDEBAR CLIENT
        sidebar = new VBox(15);
        sidebar.getStyleClass().add("sidebar");
        sidebar.setPrefWidth(280);
        sidebar.setPadding(new Insets(30, 20, 30, 20));

        // Message de bienvenue personnalisé
        Label welcomeUserLabel = new Label("Salut, " + client.getPrenom() + " ! ✨");
        welcomeUserLabel.getStyleClass().add("sidebar-brand");
        welcomeUserLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: #1abc9c;");

        // Boutons de navigation du client
        Button btnCatalogue = createNavButton("🎬 Films à l'affiche");
        Button btnMesReservations = createNavButton("🎟 Mes Tickets");
        
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        Button btnLogout = new Button("Déconnexion");
        btnLogout.getStyleClass().add("btn-logout");
        
        // Icône de déconnexion
        javafx.scene.shape.SVGPath logoutIcon = new javafx.scene.shape.SVGPath();
        logoutIcon.setContent("M10 22H5C4.44772 22 4 21.5523 4 21V3C4 2.44772 4.44772 2 5 2H10C10.5523 2 11 2.44772 11 3C11 3.55228 10.5523 4 10 4H6V20H10C10.5523 20 11 20.4477 11 21C11 21.5523 10.5523 22 10 22ZM15.0355 16.0355L20.0711 11L15.0355 5.96447L13.6213 7.37868L16.2426 10H8V12H16.2426L13.6213 14.6213L15.0355 16.0355Z");
        logoutIcon.setFill(javafx.scene.paint.Color.WHITE);
        btnLogout.setGraphic(logoutIcon);
        btnLogout.setGraphicTextGap(15);

        sidebar.getChildren().addAll(welcomeUserLabel, btnCatalogue, btnMesReservations, spacer, btnLogout);

        // 2. ZONE CENTRALE DYNAMIQUE
        contentArea = new StackPane();
        contentArea.getStyleClass().add("work-area"); // Chargera l'image right_sidebar automatiquement
        
        showWelcomeMessage();

        root.setLeft(sidebar);
        root.setCenter(contentArea);

        // Liaison contrôleur
        controller = new ClientDashboardController(this, stage, clientConnecte);

        // Actions
        btnCatalogue.setOnAction(e -> controller.loadCatalogue());
        btnMesReservations.setOnAction(e -> controller.loadMesReservations());
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
        
        Label title = new Label("Bienvenue au Cinéma, " + clientConnecte.getPrenom());
        title.getStyleClass().add("titre-welcome");
        
        Label sub = new Label("Réservez vos places en quelques clics. Choisissez une option à gauche.");
        sub.setStyle("-fx-text-fill: white; -fx-font-size: 15px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 3, 0, 1, 1);");
        
        welcomeBox.getChildren().addAll(title, sub);
        contentArea.getChildren().add(welcomeBox);
    }

    public BorderPane getView() { return root; }
    public StackPane getContentArea() { return contentArea; }
}