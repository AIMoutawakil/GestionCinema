package com.cinema.view;

import com.cinema.controller.RegisterController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RegisterView {
    private StackPane root;
    private TextField nomField;
    private TextField prenomField;
    private TextField emailField;
    private PasswordField passwordField;
    private Button registerButton;
    private Button loginLink;

    public RegisterView(Stage stage) {
        root = new StackPane();
        root.getStyleClass().add("login-root");

        VBox formCard = new VBox(15);
        formCard.getStyleClass().add("form-card");
        formCard.setMaxWidth(350);
        formCard.setAlignment(Pos.CENTER);
        formCard.setPadding(new Insets(35));

        Label titleLabel = new Label("Inscription");
        titleLabel.getStyleClass().add("titre-login");

        nomField = new TextField();
        nomField.setPromptText("Nom");
        nomField.getStyleClass().add("input-field");

        prenomField = new TextField();
        prenomField.setPromptText("Prénom");
        prenomField.getStyleClass().add("input-field");

        emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.getStyleClass().add("input-field");

        passwordField = new PasswordField();
        passwordField.setPromptText("Mot de passe");
        passwordField.getStyleClass().add("input-field");

        registerButton = new Button("S'inscrire");
        registerButton.setMaxWidth(Double.MAX_VALUE);
        registerButton.getStyleClass().add("login-button");

        loginLink = new Button("Déjà un compte ? Se connecter");
        loginLink.getStyleClass().add("link-button");

        formCard.getChildren().addAll(titleLabel, nomField, prenomField, emailField, passwordField, registerButton, loginLink);
        root.getChildren().add(formCard);

        RegisterController controller = new RegisterController(this, stage);
        registerButton.setOnAction(e -> controller.handleRegister());
        loginLink.setOnAction(e -> controller.navigateToLogin());
    }

    public StackPane getView() { return root; }
    public String getNom() { return nomField.getText(); }
    public String getPrenom() { return prenomField.getText(); }
    public String getEmail() { return emailField.getText(); }
    public String getPassword() { return passwordField.getText(); }
}
