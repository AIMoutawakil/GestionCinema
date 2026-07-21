package com.cinema.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class StatistiquesDAO {
    private Connection connection;

    public StatistiquesDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    // 1. Total des tickets vendus (uniquement les réservations confirmées)
    public int getTotalTicketsVendus() {
        String sql = "SELECT SUM(nombre_places) AS total FROM Reservation WHERE statut = 'Confirmée'";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) return rs.getInt("total");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 2. Chiffre d'affaires total (Nombre de places * Tarif de la séance)
    public double getChiffreAffaires() {
        String sql = "SELECT SUM(r.nombre_places * s.tarif) AS total_revenus " +
                     "FROM Reservation r " +
                     "INNER JOIN Seance s ON r.id_seance = s.id_seance " +
                     "WHERE r.statut = 'Confirmée'";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) return rs.getDouble("total_revenus");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    // 3. Nombre total de films à l'affiche
    public int getTotalFilms() {
        String sql = "SELECT COUNT(*) AS total FROM Film";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) return rs.getInt("total");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 4. Nombre total de clients inscrits
    public int getTotalClients() {
        String sql = "SELECT COUNT(*) AS total FROM Client";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) return rs.getInt("total");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 5. POUR LE GRAPHIQUE HISTOGRAMME : Le Top 4 des films les plus vendus
    public Map<String, Integer> getTopFilms() {
        // On utilise un LinkedHashMap pour conserver l'ordre du classement (du 1er au dernier)
        Map<String, Integer> topFilms = new LinkedHashMap<>();
        String sql = "SELECT f.titre, SUM(r.nombre_places) as total_places " +
                     "FROM Reservation r " +
                     "INNER JOIN Seance s ON r.id_seance = s.id_seance " +
                     "INNER JOIN Film f ON s.id_film = f.id_film " +
                     "WHERE r.statut = 'Confirmée' " +
                     "GROUP BY f.titre " +
                     "ORDER BY total_places DESC " +
                     "LIMIT 4"; // On prend les 4 meilleurs

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                topFilms.put(rs.getString("titre"), rs.getInt("total_places"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return topFilms;
    }
}