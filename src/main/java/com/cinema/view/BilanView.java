package com.cinema.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class BilanView {
    private VBox root;
    
    // Les labels qui contiendront les chiffres dynamiques
    private Label lblTotalTickets;
    private Label lblChiffreAffaires;
    private Label lblTotalFilms;
    private Label lblTotalClients;
    
    // Le graphique
    private BarChart<String, Number> barChart;

    public BilanView() {
        root = new VBox(30);
        root.setPadding(new Insets(20));
        root.getStyleClass().add("crud-card"); // Utilise ton fond blanc stylisé

        Label titleLabel = new Label("📊 Tableau de Bord & Bilan");
        titleLabel.setStyle("-fx-font-size: 26px; -fx-text-fill: #2c3e50; -fx-font-weight: bold;");

        // --- 1. ZONE DES CARTES KPI (En haut) ---
        HBox kpiContainer = new HBox(20);
        kpiContainer.setAlignment(Pos.CENTER);

        // Création des 4 cartes avec des valeurs temporaires (0)
        VBox cardTickets = createKpiCard("Tickets Vendus", "🎟️", "#3498db");
        lblTotalTickets = (Label) cardTickets.getChildren().get(2);

        VBox cardRevenus = createKpiCard("Chiffre d'Affaires", "💰", "#2ecc71");
        lblChiffreAffaires = (Label) cardRevenus.getChildren().get(2);

        VBox cardFilms = createKpiCard("Films à l'Affiche", "🎬", "#9b59b6");
        lblTotalFilms = (Label) cardFilms.getChildren().get(2);

        VBox cardClients = createKpiCard("Total Clients", "👥", "#f1c40f");
        lblTotalClients = (Label) cardClients.getChildren().get(2);

        kpiContainer.getChildren().addAll(cardTickets, cardRevenus, cardFilms, cardClients);

        // --- 2. ZONE DU GRAPHIQUE (En bas) ---
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Films");
        
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Nombre de places vendues");

        barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Top 4 des Films les plus populaires");
        barChart.setLegendVisible(false); // On cache la légende inutile ici
        VBox.setVgrow(barChart, Priority.ALWAYS); // Rend le graphique dynamique en hauteur

        root.getChildren().addAll(titleLabel, kpiContainer, barChart);
    }

    // Méthode pour dessiner une petite "carte" de statistique
    private VBox createKpiCard(String title, String icon, String color) {
        VBox card = new VBox(10);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 15px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 5);");
        
        // Pour que les cartes s'étirent et s'adaptent à l'écran
        HBox.setHgrow(card, Priority.ALWAYS); 

        Label iconLabel = new Label(icon);
        iconLabel.setStyle("-fx-font-size: 30px;");

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #7f8c8d; -fx-font-weight: bold;");

        Label valueLabel = new Label("0");
        valueLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: " + color + "; -fx-font-weight: bold;");

        card.getChildren().addAll(iconLabel, titleLabel, valueLabel);
        return card;
    }

    // Getters pour le contrôleur
    public VBox getView() { return root; }
    public Label getLblTotalTickets() { return lblTotalTickets; }
    public Label getLblChiffreAffaires() { return lblChiffreAffaires; }
    public Label getLblTotalFilms() { return lblTotalFilms; }
    public Label getLblTotalClients() { return lblTotalClients; }
    public BarChart<String, Number> getBarChart() { return barChart; }
}