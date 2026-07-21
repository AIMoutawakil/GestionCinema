package com.cinema.controller;

import com.cinema.dao.SalleDAO;
import com.cinema.model.Salle;
import com.cinema.view.SalleManagementView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.util.Optional;

public class SalleManagementController {
    private SalleManagementView view;
    private SalleDAO salleDAO;
    private ObservableList<Salle> masterData;

    public SalleManagementController(SalleManagementView view) {
        this.view = view;
        this.salleDAO = new SalleDAO(); // Assure-toi que ta classe SalleDAO existe
        this.masterData = FXCollections.observableArrayList();

        // Initialisation
        loadData();
        setupSearchFilter();

        // Clics sur les boutons
        view.getBtnAdd().setOnAction(e -> handleAdd());
        view.getBtnEdit().setOnAction(e -> handleEdit());
        view.getBtnDelete().setOnAction(e -> handleDelete());
    }

    // --- CHARGEMENT DES DONNÉES ---
    private void loadData() {
        masterData.setAll(salleDAO.listerSalles());
    }

    // --- BARRE DE RECHERCHE ---
    private void setupSearchFilter() {
        FilteredList<Salle> filteredData = new FilteredList<>(masterData, p -> true);
        
        view.getSearchField().textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(salle -> {
                if (newValue == null || newValue.isEmpty()) return true;
                
                String lowerCaseFilter = newValue.toLowerCase();
                
                // CORRECTION ICI : Utilisation de getTechnologie()
                return salle.getNomSalle().toLowerCase().contains(lowerCaseFilter) || 
                       salle.getTechnologie().toLowerCase().contains(lowerCaseFilter);
            });
        });
        
        SortedList<Salle> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(view.getTable().comparatorProperty());
        view.getTable().setItems(sortedData);
    }

    // --- AJOUTER ---
    private void handleAdd() {
        Optional<Salle> result = showSalleFormDialog(null);
        result.ifPresent(nouvelleSalle -> {
            if (salleDAO.ajouterSalle(nouvelleSalle)) {
                loadData();
                showAlert(Alert.AlertType.INFORMATION, "Succès", "La salle a été ajoutée.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "L'ajout a échoué.");
            }
        });
    }

    // --- MODIFIER ---
    private void handleEdit() {
        Salle selectedSalle = view.getTable().getSelectionModel().getSelectedItem();
        if (selectedSalle != null) {
            Optional<Salle> result = showSalleFormDialog(selectedSalle);
            result.ifPresent(salleModifiee -> {
                salleModifiee.setIdSalle(selectedSalle.getIdSalle()); 
                if (salleDAO.modifierSalle(salleModifiee)) {
                    loadData();
                    showAlert(Alert.AlertType.INFORMATION, "Succès", "La salle a été modifiée.");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "La modification a échoué.");
                }
            });
        } else {
            showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez sélectionner une salle à modifier.");
        }
    }

    // --- SUPPRIMER ---
    private void handleDelete() {
        Salle selectedSalle = view.getTable().getSelectionModel().getSelectedItem();
        if (selectedSalle != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirmation");
            confirm.setHeaderText(null);
            confirm.setContentText("Êtes-vous sûr de vouloir supprimer la salle : " + selectedSalle.getNomSalle() + " ?");
            
            if (confirm.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
                if(salleDAO.supprimerSalle(selectedSalle.getIdSalle())) {
                    loadData();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "La suppression a échoué.");
                }
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez sélectionner une salle à supprimer.");
        }
    }

    // --- LE FORMULAIRE POP-UP (Même design que le Login) ---
    private Optional<Salle> showSalleFormDialog(Salle salleToEdit) {
        Dialog<Salle> dialog = new Dialog<>();
        dialog.setTitle(salleToEdit == null ? "Ajouter une salle" : "Modifier la salle");
        DialogPane dialogPane = dialog.getDialogPane();
        
        // Chargement du CSS
        try { 
            dialogPane.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm()); 
        } catch (Exception e) {}
        
        dialogPane.getStyleClass().add("form-card"); 

        // Boutons
        ButtonType saveBtnType = new ButtonType("Sauvegarder", ButtonBar.ButtonData.OK_DONE);
        dialogPane.getButtonTypes().addAll(saveBtnType, ButtonType.CANCEL);
        
        Button saveButton = (Button) dialogPane.lookupButton(saveBtnType);
        saveButton.getStyleClass().add("login-button");
        
        Button cancelButton = (Button) dialogPane.lookupButton(ButtonType.CANCEL);
        cancelButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #a4b0be; -fx-cursor: hand; -fx-font-weight: bold;");

        // Champs de saisie
        VBox vbox = new VBox(15);
        vbox.setAlignment(javafx.geometry.Pos.CENTER);
        vbox.setPadding(new Insets(20, 30, 20, 30));

        Label titleLabel = new Label(salleToEdit == null ? "Nouvelle Salle" : "Mise à jour");
        titleLabel.getStyleClass().add("titre-login");

        TextField nom = new TextField();
        nom.setPromptText("Nom de la salle (ex: Salle 1)");
        nom.getStyleClass().add("input-field");

        TextField capacite = new TextField();
        capacite.setPromptText("Capacité (nombre de sièges)");
        capacite.getStyleClass().add("input-field");

        // Liste déroulante pour la technologie
        ComboBox<String> typeEcranBox = new ComboBox<>();
        typeEcranBox.getItems().addAll("Standard", "3D", "IMAX", "4DX", "Dolby Cinema");
        typeEcranBox.setPromptText("Technologie");
        typeEcranBox.getStyleClass().add("input-field");
        typeEcranBox.setMaxWidth(Double.MAX_VALUE);

        // Pré-remplissage
        if (salleToEdit != null) {
            nom.setText(salleToEdit.getNomSalle());
            capacite.setText(String.valueOf(salleToEdit.getCapacite()));
            
            // CORRECTION ICI : Utilisation de getTechnologie()
            typeEcranBox.setValue(salleToEdit.getTechnologie()); 
        }

        vbox.getChildren().addAll(titleLabel, nom, capacite, typeEcranBox);
        dialogPane.setContent(vbox);

        // Validation du formulaire
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveBtnType) {
                try {
                    int cap = Integer.parseInt(capacite.getText());
                    String tech = typeEcranBox.getValue() != null ? typeEcranBox.getValue() : "Standard";
                    
                    // Création de l'objet Salle
                    return new Salle(nom.getText(), cap, tech);
                } catch (NumberFormatException e) {
                    showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "La capacité doit être un nombre entier.");
                    return null;
                }
            }
            return null;
        });

        return dialog.showAndWait();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null); 
        alert.setTitle(title); 
        alert.setContentText(content); 
        alert.showAndWait();
    }
}