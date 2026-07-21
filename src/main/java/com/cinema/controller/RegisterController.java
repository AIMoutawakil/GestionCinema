package com.cinema.controller;

import com.cinema.dao.ClientDAO;
import com.cinema.model.Client;
import com.cinema.view.LoginView;
import com.cinema.view.RegisterView;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class RegisterController {
    private RegisterView view;
    private Stage stage;
    private ClientDAO clientDAO;

    public RegisterController(RegisterView view, Stage stage) {
        this.view = view;
        this.stage = stage;
        this.clientDAO = new ClientDAO();
    }

    public void handleRegister() {
        String nom = view.getNom();
        String prenom = view.getPrenom();
        String email = view.getEmail();
        String password = view.getPassword();

        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Champs vides", "Veuillez remplir tous les champs.");
            return;
        }

        Client nouveauClient = new Client(nom, prenom, email, password);
        boolean succes = clientDAO.ajouterClient(nouveauClient);

        if (succes) {
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Compte créé avec succès ! Connectez-vous maintenant.");
            navigateToLogin();
        } else {
            showAlert(Alert.AlertType.ERROR, "Échec", "Erreur lors de l'inscription. L'email est peut-être déjà utilisé.");
        }
    }

    public void navigateToLogin() {
        LoginView loginView = new LoginView(stage);
        stage.getScene().setRoot(loginView.getView());
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
