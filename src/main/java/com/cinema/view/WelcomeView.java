package com.cinema.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WelcomeView {
    private StackPane root;

    public WelcomeView(Stage stage) {
        root = new StackPane();
        root.getStyleClass().add("welcome-root");

        Button welcomeButton = new Button("Bienvenue");
        welcomeButton.getStyleClass().add("btn-welcome");

        welcomeButton.setOnAction(e -> {
            LoginView loginView = new LoginView(stage);
            stage.getScene().setRoot(loginView.getView());
        });

        VBox buttonContainer = new VBox();
        buttonContainer.setAlignment(Pos.BOTTOM_CENTER);
        buttonContainer.setStyle("-fx-padding: 0 0 80px 0;");
        buttonContainer.getChildren().add(welcomeButton);

        root.getChildren().add(buttonContainer);
    }

    public StackPane getView() {
        return root;
    }
}
