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
    private StackPane contentArea; // Zone qui va changer de contenu
    private AdminDashboardController controller;

    public AdminDashboardView(Stage stage) {
        root = new BorderPane();
        root.getStyleClass().add("dashboard-root"); // Ton image rouge couchée

        // 1. CRÉATION DE LA SIDEBAR (Gauche)
        sidebar = new VBox(15);
        sidebar.getStyleClass().add("sidebar");
        sidebar.setPrefWidth(280); // <--- Élargit un peu pour bien voir l'image
        sidebar.setPadding(new Insets(30, 20, 30, 20));

        // Titre "CINÉMA ADMIN" avec le style de ton logo beige/bleu
        Label brandLabel = new Label("CINÉMA ADMIN");
        brandLabel.getStyleClass().add("sidebar-brand");

        // Boutons de navigation (Nous créerons les vues Films et Salles juste après)
        Button btnBilan = createNavButton("📊 Tableau de Bord");
        Button btnFilms = createNavButton("Gestion des Films");
        Button btnSalles = createNavButton("Gestion des Salles");
        Button btnSeances = createNavButton("Planning Séances");
        Button btnReservations = createNavButton("Réservations");
        
        // Un espaceur pour pousser le bouton déconnexion en bas
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

     // --- BOUTON DÉCONNEXION AVEC ICÔNE ---
        Button btnLogout = new Button("Déconnexion");
        btnLogout.getStyleClass().add("btn-logout");
        
        // Création de l'icône SVG en JavaFX
        javafx.scene.shape.SVGPath logoutIcon = new javafx.scene.shape.SVGPath();
        // Le code du dessin (Path) de l'icône de déconnexion
        logoutIcon.setContent("M10 22H5C4.44772 22 4 21.5523 4 21V3C4 2.44772 4.44772 2 5 2H10C10.5523 2 11 2.44772 11 3C11 3.55228 10.5523 4 10 4H6V20H10C10.5523 20 11 20.4477 11 21C11 21.5523 10.5523 22 10 22ZM15.0355 16.0355L20.0711 11L15.0355 5.96447L13.6213 7.37868L16.2426 10H8V12H16.2426L13.6213 14.6213L15.0355 16.0355Z");
        logoutIcon.setFill(javafx.scene.paint.Color.WHITE); // Icône en blanc
        
        // On attache l'icône au bouton
        btnLogout.setGraphic(logoutIcon);
        btnLogout.setGraphicTextGap(15); // L'espace entre l'icône et le texte

        sidebar.getChildren().addAll(brandLabel, btnBilan, btnFilms, btnSalles, btnSeances, btnReservations, spacer, btnLogout);
        // 2. ZONE DE CONTENU DYNAMIQUE (Centre)
        contentArea = new StackPane();
        contentArea.getStyleClass().add("work-area"); // Nouvelle classe CSS pour la zone de travail
        
        // Par défaut, on affiche une page de bienvenue
        showWelcomeMessage();

        // Ajout des parties au BorderPane
        root.setLeft(sidebar);
        root.setCenter(contentArea);

        // Liaison avec le contrôleur
        controller = new AdminDashboardController(this, stage);
        
        // Actions des boutons
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

    // Affiche un message central de bienvenue
    public void showWelcomeMessage() {
        contentArea.getChildren().clear();
        VBox welcomeBox = new VBox(10);
        welcomeBox.setAlignment(Pos.CENTER);
        
        Label title = new Label("Bienvenue dans votre Espace de Gestion");
        title.getStyleClass().add("titre-welcome"); // On ajoute une classe CSS
        
        Label sub = new Label("Sélectionnez une option à gauche pour commencer.");
        sub.setStyle("-fx-text-fill: #a4b0be; -fx-font-size: 14px;");
        
        welcomeBox.getChildren().addAll(title, sub);
        contentArea.getChildren().add(welcomeBox);
    }

    public BorderPane getView() { return root; }
    public StackPane getContentArea() { return contentArea; }
}