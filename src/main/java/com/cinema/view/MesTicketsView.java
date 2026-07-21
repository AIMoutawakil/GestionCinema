package com.cinema.view;

import com.cinema.model.Reservation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import java.time.LocalDateTime;

public class MesTicketsView {
    private VBox root;
    private TableView<Reservation> table;
    private Button btnCancel;

    public MesTicketsView() {
        root = new VBox(20);
        root.getStyleClass().add("crud-card"); // Fond blanc semi-transparent

        Label titleLabel = new Label("Mes Réservations");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: #16a085; -fx-font-weight: bold;");

        HBox toolbar = new HBox();
        toolbar.setAlignment(Pos.CENTER_RIGHT);

        btnCancel = new Button("🚫 Annuler mon billet");
        btnCancel.getStyleClass().addAll("btn-action", "btn-delete");
        toolbar.getChildren().add(btnCancel);

        table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        VBox.setVgrow(table, Priority.ALWAYS);

        TableColumn<Reservation, String> colFilm = new TableColumn<>("Film");
        colFilm.setCellValueFactory(new PropertyValueFactory<>("titreFilm"));

        TableColumn<Reservation, LocalDateTime> colSeance = new TableColumn<>("Date & Heure de la séance");
        colSeance.setCellValueFactory(new PropertyValueFactory<>("dateSeance"));

        TableColumn<Reservation, Integer> colPlaces = new TableColumn<>("Places réservées");
        colPlaces.setCellValueFactory(new PropertyValueFactory<>("nombrePlaces"));

        TableColumn<Reservation, String> colStatut = new TableColumn<>("Statut");
        colStatut.setCellValueFactory(new PropertyValueFactory<>("statut"));

        table.getColumns().addAll(colFilm, colSeance, colPlaces, colStatut);

        root.getChildren().addAll(titleLabel, toolbar, table);
    }

    public VBox getView() { return root; }
    public TableView<Reservation> getTable() { return table; }
    public Button getBtnCancel() { return btnCancel; }
}