package com.cinema.dao;

import com.cinema.model.Seance;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeanceDAO {
    private Connection connection;

    public SeanceDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public boolean ajouterSeance(Seance seance) {
        String sql = "INSERT INTO seance (id_film, id_salle, date_projection, heure_debut, tarif) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, seance.getIdFilm());
            stmt.setInt(2, seance.getIdSalle());
            stmt.setDate(3, Date.valueOf(seance.getDateProjection()));
            stmt.setTime(4, Time.valueOf(seance.getHeureDebut()));
            stmt.setDouble(5, seance.getTarif());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Seance> listerSeancesParFilm(int idFilm) {
        List<Seance> seances = new ArrayList<>();
        String sql = "SELECT s.id_seance, s.id_film, s.id_salle, s.date_projection, s.heure_debut, s.tarif, " +
                     "f.titre AS titre_film, sa.nom_salle " +
                     "FROM seance s " +
                     "INNER JOIN film f ON s.id_film = f.id_film " +
                     "INNER JOIN salle sa ON s.id_salle = sa.id_salle " +
                     "WHERE s.id_film = ? AND s.date_projection >= CURRENT_DATE " + // Uniquement les séances futures ou du jour
                     "ORDER BY s.date_projection, s.heure_debut";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idFilm);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    seances.add(new Seance(
                        rs.getInt("id_seance"),
                        rs.getInt("id_film"),
                        rs.getInt("id_salle"),
                        rs.getDate("date_projection").toLocalDate(),
                        rs.getTime("heure_debut").toLocalTime(),
                        rs.getDouble("tarif"),
                        rs.getString("titre_film"),
                        rs.getString("nom_salle")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seances;
    }
    public List<Seance> listerSeances() {
        List<Seance> seances = new ArrayList<>();
        String sql = "SELECT s.id_seance, s.id_film, s.id_salle, s.date_projection, s.heure_debut, s.tarif, " +
                     "f.titre AS titre_film, sa.nom_salle " +
                     "FROM seance s " +
                     "INNER JOIN film f ON s.id_film = f.id_film " +
                     "INNER JOIN salle sa ON s.id_salle = sa.id_salle";
                     
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                seances.add(new Seance(
                    rs.getInt("id_seance"),
                    rs.getInt("id_film"),
                    rs.getInt("id_salle"),
                    rs.getDate("date_projection").toLocalDate(),
                    rs.getTime("heure_debut").toLocalTime(),
                    rs.getDouble("tarif"),
                    rs.getString("titre_film"), // Récupéré grâce au JOIN
                    rs.getString("nom_salle")   // Récupéré grâce au JOIN
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seances;
    }

    public boolean modifierSeance(Seance seance) {
        String sql = "UPDATE seance SET id_film = ?, id_salle = ?, date_projection = ?, heure_debut = ?, tarif = ? WHERE id_seance = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, seance.getIdFilm());
            stmt.setInt(2, seance.getIdSalle());
            stmt.setDate(3, Date.valueOf(seance.getDateProjection()));
            stmt.setTime(4, Time.valueOf(seance.getHeureDebut()));
            stmt.setDouble(5, seance.getTarif());
            stmt.setInt(6, seance.getIdSeance());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean supprimerSeance(int idSeance) {
        String sql = "DELETE FROM seance WHERE id_seance = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idSeance);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
