package com.cinema.view;

import com.cinema.model.Reservation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import java.time.LocalDateTime;

public class ReservationManagementView {
    private VBox root;
    private TextField searchField;
    private Button btnCancel;
    private TableView<Reservation> table;

    public ReservationManagementView() {
        root = new VBox(20);
        root.getStyleClass().add("crud-card");

        Label titleLabel = new Label("Suivi des Réservations");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: #16a085; -fx-font-weight: bold;");

        HBox toolbar = new HBox(15);
        toolbar.setAlignment(Pos.CENTER_LEFT);

        searchField = new TextField();
        searchField.setPromptText("Rechercher un client ou un film...");
        searchField.setPrefWidth(300);
        searchField.setStyle("-fx-background-radius: 20px; -fx-border-radius: 20px; -fx-padding: 8px 15px;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        btnCancel = new Button("🚫 Annuler Réservation");
        btnCancel.getStyleClass().addAll("btn-action", "btn-delete");

        toolbar.getChildren().addAll(searchField, spacer, btnCancel);

        table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        VBox.setVgrow(table, Priority.ALWAYS);

        TableColumn<Reservation, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("idReservation"));
        colId.setMaxWidth(50);

        TableColumn<Reservation, String> colClient = new TableColumn<>("Client");
        colClient.setCellValueFactory(new PropertyValueFactory<>("nomClient"));

        TableColumn<Reservation, String> colFilm = new TableColumn<>("Film");
        colFilm.setCellValueFactory(new PropertyValueFactory<>("titreFilm"));

        TableColumn<Reservation, LocalDateTime> colSeance = new TableColumn<>("Date Séance");
        colSeance.setCellValueFactory(new PropertyValueFactory<>("dateSeance"));

        TableColumn<Reservation, Integer> colPlaces = new TableColumn<>("Places");
        colPlaces.setCellValueFactory(new PropertyValueFactory<>("nombrePlaces"));

        TableColumn<Reservation, String> colStatut = new TableColumn<>("Statut");
        colStatut.setCellValueFactory(new PropertyValueFactory<>("statut"));

        table.getColumns().addAll(colId, colClient, colFilm, colSeance, colPlaces, colStatut);

        root.getChildren().addAll(titleLabel, toolbar, table);
    }

    public VBox getView() { return root; }
    public TableView<Reservation> getTable() { return table; }
    public Button getBtnCancel() { return btnCancel; }
    public TextField getSearchField() { return searchField; }
}
