package com.cinema.controller;

import com.cinema.dao.AdminDAO;
import com.cinema.dao.ClientDAO;
import com.cinema.model.Admin;
import com.cinema.model.Client;
import com.cinema.view.AdminDashboardView;
import com.cinema.view.LoginView;
import com.cinema.view.RegisterView;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.Optional;

public class LoginController {
    private LoginView view;
    private Stage stage;
    private ClientDAO clientDAO;
    private AdminDAO adminDAO; // Ajout du DAO Administrateur

    public LoginController(LoginView view, Stage stage) {
        this.view = view;
        this.stage = stage;
        this.clientDAO = new ClientDAO();
        this.adminDAO = new AdminDAO(); // Initialisation
    }

    // --- CONNEXION CLIENT ---
 // --- CONNEXION CLIENT ---
    public void handleLogin() {
        String email = view.getEmail();
        String password = view.getPassword();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        Client client = clientDAO.authentifierClient(email, password);
        if (client != null) {
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Bienvenue " + client.getPrenom() + " !");
            
            // --- MODIFICATION ICI : REDIRECTION VERS L'ESPACE CLIENT ---
            com.cinema.view.ClientDashboardView clientHome = new com.cinema.view.ClientDashboardView(stage, client);
            stage.getScene().setRoot(clientHome.getView());
            stage.setWidth(1100); // Même taille confortable que l'admin
            stage.setHeight(700);
            stage.centerOnScreen();
            
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Identifiants clients incorrects.");
        }
    }

    // --- NAVIGATION VERS INSCRIPTION ---
    public void navigateToRegister() {
        RegisterView registerView = new RegisterView(stage);
        stage.getScene().setRoot(registerView.getView());
    }

    // --- ACCÈS SÉCURISÉ ADMINISTRATEUR (Modifié pour utiliser la BDD) ---
 // --- ACCÈS SÉCURISÉ ADMINISTRATEUR ---
    public void handleAdminAccess() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Accès Administrateur");
        // On retire le HeaderText pour un design plus épuré
        dialog.setHeaderText(null); 

        DialogPane dialogPane = dialog.getDialogPane();

        // 1. CHARGEMENT DU CSS
        try {
            dialogPane.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        } catch (Exception e) {
            System.out.println("Attention: Fichier CSS introuvable pour le dialogue Admin.");
        }

        // Application de la carte blanche
        dialogPane.getStyleClass().add("form-card");

        // 2. BOUTONS
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Style du bouton OK (On change aussi son texte pour "Se connecter")
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        okButton.setText("Se connecter");
        okButton.getStyleClass().add("login-button");

        // Style du bouton Annuler
        Button cancelButton = (Button) dialogPane.lookupButton(ButtonType.CANCEL);
        cancelButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #a4b0be; -fx-cursor: hand; -fx-font-weight: bold;");

        // 3. FORMULAIRE (VBox)
        VBox vbox = new VBox(15);
        vbox.setAlignment(javafx.geometry.Pos.CENTER);
        vbox.setPadding(new Insets(20, 30, 20, 30));

        // Titre
        Label titleLabel = new Label("Espace Admin");
        titleLabel.getStyleClass().add("titre-login");

        // Champs de saisie stylisés
        TextField emailAdminField = new TextField();
        emailAdminField.setPromptText("Email Administrateur");
        emailAdminField.getStyleClass().add("input-field");

        PasswordField passAdminField = new PasswordField();
        passAdminField.setPromptText("Mot de passe");
        passAdminField.getStyleClass().add("input-field");

        vbox.getChildren().addAll(titleLabel, emailAdminField, passAdminField);
        dialogPane.setContent(vbox);

        // 4. ACTION ET VÉRIFICATION
        Optional<ButtonType> result = dialog.showAndWait();
        
        if (result.isPresent() && result.get() == ButtonType.OK) {
            String emailAdmin = emailAdminField.getText();
            String passAdmin = passAdminField.getText();

            if (emailAdmin.isEmpty() || passAdmin.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Attention", "Les champs administrateur ne peuvent pas être vides.");
                return;
            }

            // Vérification dans la base de données via AdminDAO
            Admin admin = adminDAO.authentifierAdmin(emailAdmin, passAdmin);
            
            if (admin != null) {
                showAlert(Alert.AlertType.INFORMATION, "Accès Autorisé", "Bienvenue Directeur " + admin.getPrenom() + ".");
                
                // REDIRECTION VERS LE DASHBOARD
                com.cinema.view.AdminDashboardView dashboard = new com.cinema.view.AdminDashboardView(stage);
                stage.getScene().setRoot(dashboard.getView());
                stage.setWidth(1100); 
                stage.setHeight(700);
                stage.centerOnScreen();
            } else {
                showAlert(Alert.AlertType.ERROR, "Accès Refusé", "Email ou mot de passe administrateur incorrect.");
            }
        }
    }

    // --- MÉTHODE UTILITAIRE POUR LES POP-UPS ---
    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}