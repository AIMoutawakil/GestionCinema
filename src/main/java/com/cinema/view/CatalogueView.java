package com.cinema.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;

public class CatalogueView {
    private ScrollPane root;
    private FlowPane grid;

    public CatalogueView() {
        root = new ScrollPane();
        root.setFitToWidth(true); // Adapte la largeur à l'écran
        // On rend le fond transparent pour ne pas cacher ta belle image rouge
        root.setStyle("-fx-background-color: transparent; -fx-background: transparent;");

        grid = new FlowPane();
        grid.setPadding(new Insets(30));
        grid.setHgap(25); // Espace horizontal entre les cartes
        grid.setVgap(25); // Espace vertical entre les cartes
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setStyle("-fx-background-color: transparent;");

        root.setContent(grid);
    }

    public FlowPane getGrid() { return grid; }
    public ScrollPane getView() { return root; }
}