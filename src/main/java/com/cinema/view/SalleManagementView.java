package com.cinema.view;

import com.cinema.model.Salle;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

public class SalleManagementView {
    private VBox root;
    private TextField searchField;
    private Button btnAdd, btnEdit, btnDelete;
    private TableView<Salle> table;

    public SalleManagementView() {
        root = new VBox(20);
        root.getStyleClass().add("crud-card"); // Fond blanc semi-transparent

        // Titre
        Label titleLabel = new Label("Gestion des Salles");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: #16a085; -fx-font-weight: bold;");

        // Barre d'outils
        HBox toolbar = new HBox(15);
        toolbar.setAlignment(Pos.CENTER_LEFT);

        searchField = new TextField();
        searchField.setPromptText("Rechercher une salle...");
        searchField.setPrefWidth(250);
        searchField.setStyle("-fx-background-radius: 20px; -fx-border-radius: 20px; -fx-padding: 8px 15px;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        btnEdit = new Button("✎ Modifier");
        btnEdit.getStyleClass().addAll("btn-action", "btn-edit");

        btnDelete = new Button("🗑 Supprimer");
        btnDelete.getStyleClass().addAll("btn-action", "btn-delete");

        btnAdd = new Button("+ Ajouter");
        btnAdd.getStyleClass().addAll("btn-action", "btn-add");

        toolbar.getChildren().addAll(searchField, spacer, btnEdit, btnDelete, btnAdd);

        // Tableau
        table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        VBox.setVgrow(table, Priority.ALWAYS);

        // Colonnes du tableau (liées aux attributs de ta classe Salle)
        TableColumn<Salle, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("idSalle"));
        colId.setMaxWidth(50);

        TableColumn<Salle, String> colNom = new TableColumn<>("Nom de la Salle");
        colNom.setCellValueFactory(new PropertyValueFactory<>("nomSalle"));

        TableColumn<Salle, Integer> colCapacite = new TableColumn<>("Capacité (Sièges)");
        colCapacite.setCellValueFactory(new PropertyValueFactory<>("capacite"));

        // CORRECTION ICI : Utilisation de "technologie"
        TableColumn<Salle, String> colType = new TableColumn<>("Technologie");
        colType.setCellValueFactory(new PropertyValueFactory<>("technologie"));

        table.getColumns().addAll(colId, colNom, colCapacite, colType);

        root.getChildren().addAll(titleLabel, toolbar, table);
    }

    // --- GETTERS POUR LE CONTRÔLEUR ---
    public VBox getView() { return root; }
    public TableView<Salle> getTable() { return table; }
    public Button getBtnAdd() { return btnAdd; }
    public Button getBtnEdit() { return btnEdit; }
    public Button getBtnDelete() { return btnDelete; }
    public TextField getSearchField() { return searchField; }
}