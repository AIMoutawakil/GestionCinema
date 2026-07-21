package com.cinema.view;

import com.cinema.model.Seance;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class SeanceManagementView {
    private VBox root;
    private TextField searchField;
    private Button btnAdd, btnEdit, btnDelete;
    private TableView<Seance> table;

    public SeanceManagementView() {
        root = new VBox(20);
        root.getStyleClass().add("crud-card"); 

        Label titleLabel = new Label("Planning des Séances");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: #16a085; -fx-font-weight: bold;");

        HBox toolbar = new HBox(15);
        toolbar.setAlignment(Pos.CENTER_LEFT);

        searchField = new TextField();
        searchField.setPromptText("Rechercher un film ou une salle...");
        searchField.setPrefWidth(300);
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

        table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        VBox.setVgrow(table, Priority.ALWAYS);

        // --- COLONNES DU TABLEAU ---
        TableColumn<Seance, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("idSeance"));
        colId.setMaxWidth(50);

        TableColumn<Seance, String> colFilm = new TableColumn<>("Film");
        colFilm.setCellValueFactory(new PropertyValueFactory<>("titreFilm")); // Utilise le nom récupéré par la jointure SQL

        TableColumn<Seance, String> colSalle = new TableColumn<>("Salle");
        colSalle.setCellValueFactory(new PropertyValueFactory<>("nomSalle")); // Utilise le nom récupéré par la jointure SQL

        TableColumn<Seance, LocalDate> colDate = new TableColumn<>("Date");
        colDate.setCellValueFactory(new PropertyValueFactory<>("dateProjection"));

        TableColumn<Seance, LocalTime> colHeure = new TableColumn<>("Heure");
        colHeure.setCellValueFactory(new PropertyValueFactory<>("heureDebut"));

        TableColumn<Seance, Double> colPrix = new TableColumn<>("Tarif (MAD)");
        colPrix.setCellValueFactory(new PropertyValueFactory<>("tarif"));

        table.getColumns().addAll(colId, colFilm, colSalle, colDate, colHeure, colPrix);

        root.getChildren().addAll(titleLabel, toolbar, table);
    }

    public VBox getView() { return root; }
    public TableView<Seance> getTable() { return table; }
    public Button getBtnAdd() { return btnAdd; }
    public Button getBtnEdit() { return btnEdit; }
    public Button getBtnDelete() { return btnDelete; }
    public TextField getSearchField() { return searchField; }
}