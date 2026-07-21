package com.cinema.controller;

import com.cinema.dao.StatistiquesDAO;
import com.cinema.view.BilanView;
import javafx.scene.chart.XYChart;
import java.util.Map;

public class BilanController {
    private BilanView view;
    private StatistiquesDAO statsDAO;

    public BilanController(BilanView view) {
        this.view = view;
        this.statsDAO = new StatistiquesDAO();

        chargerStatistiques();
    }

    private void chargerStatistiques() {
        // 1. Mise à jour des cartes KPI
        view.getLblTotalTickets().setText(String.valueOf(statsDAO.getTotalTicketsVendus()));
        view.getLblChiffreAffaires().setText(statsDAO.getChiffreAffaires() + " MAD");
        view.getLblTotalFilms().setText(String.valueOf(statsDAO.getTotalFilms()));
        view.getLblTotalClients().setText(String.valueOf(statsDAO.getTotalClients()));

        // 2. Mise à jour du graphique à barres
        Map<String, Integer> topFilms = statsDAO.getTopFilms();
        
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Places vendues");

        for (Map.Entry<String, Integer> entry : topFilms.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        view.getBarChart().getData().clear();
        view.getBarChart().getData().add(series);
    }
}