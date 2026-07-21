package com.cinema.view;

import com.cinema.controller.LoginController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginView {
    private StackPane root;
    private TextField emailField;
    private PasswordField passwordField;
    private Button loginButton;
    private Button registerLink;
    private Button adminLink;

    public LoginView(Stage stage) {
        root = new StackPane();
        root.getStyleClass().add("login-root");

        VBox formCard = new VBox(20);
        formCard.getStyleClass().add("form-card");
        formCard.setMaxWidth(350);
        formCard.setAlignment(Pos.CENTER);
        formCard.setPadding(new Insets(35));

        Label titleLabel = new Label("Connexion");
        titleLabel.getStyleClass().add("titre-login");

        emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.getStyleClass().add("input-field");

        passwordField = new PasswordField();
        passwordField.setPromptText("Mot de passe");
        passwordField.getStyleClass().add("input-field");

        loginButton = new Button("Se connecter");
        loginButton.setMaxWidth(Double.MAX_VALUE);
        loginButton.getStyleClass().add("login-button");

        registerLink = new Button("Pas encore de compte ? S'inscrire");
        registerLink.getStyleClass().add("link-button");

        adminLink = new Button("Espace Administrateur");
        adminLink.getStyleClass().add("link-button");
        adminLink.setStyle("-fx-text-fill: #70a1ff; -fx-font-size: 10px;"); // Style un peu plus discret

        formCard.getChildren().addAll(titleLabel, emailField, passwordField, loginButton, registerLink, adminLink);
        root.getChildren().add(formCard);

        LoginController controller = new LoginController(this, stage);
        
        // Actions
        loginButton.setOnAction(e -> controller.handleLogin());
        registerLink.setOnAction(e -> controller.navigateToRegister());
        adminLink.setOnAction(e -> controller.handleAdminAccess());
    }

    public StackPane getView() { return root; }
    public String getEmail() { return emailField.getText(); }
    public String getPassword() { return passwordField.getText(); }
}