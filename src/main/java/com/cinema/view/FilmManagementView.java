package com.cinema.view;

import com.cinema.model.Film;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

public class FilmManagementView {
    private VBox root;
    private TextField searchField;
    private Button btnAdd;
    private Button btnEdit;
    private Button btnDelete;
    private TableView<Film> table;

    public FilmManagementView() {
        root = new VBox(20);
        // On utilise la classe CSS créée précédemment pour la boîte blanche sur fond rouge
        root.getStyleClass().add("crud-card"); 
        
        // --- EN-TÊTE (Titre) ---
        Label titleLabel = new Label("Gestion des Films");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: #16a085; -fx-font-weight: bold;");

        // --- BARRE D'OUTILS (Recherche + Boutons) ---
        HBox toolbar = new HBox(15);
        toolbar.setAlignment(Pos.CENTER_LEFT);

        searchField = new TextField();
        searchField.setPromptText("Rechercher un film...");
        searchField.setPrefWidth(250);
        searchField.setStyle("-fx-background-radius: 20px; -fx-border-radius: 20px; -fx-padding: 8px 15px;");

        // Un espaceur flexible pour pousser les boutons tout à droite
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        btnEdit = new Button("✎ Modifier");
        btnEdit.getStyleClass().addAll("btn-action", "btn-edit");

        btnDelete = new Button("🗑 Supprimer");
        btnDelete.getStyleClass().addAll("btn-action", "btn-delete");

        btnAdd = new Button("+ Ajouter");
        btnAdd.getStyleClass().addAll("btn-action", "btn-add");

        toolbar.getChildren().addAll(searchField, spacer, btnEdit, btnDelete, btnAdd);

        // --- TABLEAU (TableView) ---
        table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        VBox.setVgrow(table, Priority.ALWAYS); // Le tableau prendra tout l'espace restant en hauteur

        // Création des colonnes (les noms entre guillemets doivent correspondre exactement aux attributs de la classe Film)
        TableColumn<Film, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("idFilm"));
        colId.setMaxWidth(50);

        TableColumn<Film, String> colTitre = new TableColumn<>("Titre");
        colTitre.setCellValueFactory(new PropertyValueFactory<>("titre"));

        TableColumn<Film, String> colGenre = new TableColumn<>("Genre");
        colGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));

        TableColumn<Film, Integer> colDuree = new TableColumn<>("Durée (min)");
        colDuree.setCellValueFactory(new PropertyValueFactory<>("dureeMinutes"));

        TableColumn<Film, String> colRealisateur = new TableColumn<>("Réalisateur");
        colRealisateur.setCellValueFactory(new PropertyValueFactory<>("realisateur"));

        table.getColumns().addAll(colId, colTitre, colGenre, colDuree, colRealisateur);

        // Ajout de tous les éléments à la racine
        root.getChildren().addAll(titleLabel, toolbar, table);
    }

    public VBox getView() { return root; }
    public TableView<Film> getTable() { return table; }
    public Button getBtnAdd() { return btnAdd; }
    public Button getBtnEdit() { return btnEdit; }
    public Button getBtnDelete() { return btnDelete; }
    public TextField getSearchField() { return searchField; }
}