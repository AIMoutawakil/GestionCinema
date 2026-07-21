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
        root.setFitToWidth(true);
        root.setStyle("-fx-background-color: transparent; -fx-background: transparent;");

        grid = new FlowPane();
        grid.setPadding(new Insets(30));
        grid.setHgap(25);
        grid.setVgap(25);
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setStyle("-fx-background-color: transparent;");

        root.setContent(grid);
    }

    public FlowPane getGrid() { return grid; }
    public ScrollPane getView() { return root; }
}
