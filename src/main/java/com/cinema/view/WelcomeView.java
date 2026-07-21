package com.cinema.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WelcomeView {
    private StackPane root; // StackPane permet de superposer le bouton sur le fond

    public WelcomeView(Stage stage) {
        root = new StackPane();
        root.getStyleClass().add("welcome-root"); // Le CSS va appliquer l'image de fond ici

        // Le bouton "Bienvenue"
        Button welcomeButton = new Button("Bienvenue");
        welcomeButton.getStyleClass().add("btn-welcome");

        // L'action du bouton
        welcomeButton.setOnAction(e -> {
            LoginView loginView = new LoginView(stage);
            stage.getScene().setRoot(loginView.getView());
        });

        // Un conteneur invisible juste pour placer le bouton en bas de l'écran
        VBox buttonContainer = new VBox();
        buttonContainer.setAlignment(Pos.BOTTOM_CENTER); // Aligne le bouton en bas au centre
        buttonContainer.setStyle("-fx-padding: 0 0 80px 0;"); // Ajoute une marge de 80px en bas
        buttonContainer.getChildren().add(welcomeButton);

        // On superpose : l'image de fond (gérée par CSS sur root) sera derrière, le conteneur du bouton devant
        root.getChildren().add(buttonContainer);
    }

    public StackPane getView() {
        return root;
    }
}