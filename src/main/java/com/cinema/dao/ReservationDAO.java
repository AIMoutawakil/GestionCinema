package com.cinema.dao;

import com.cinema.model.Reservation;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {
    private Connection connection;

    public ReservationDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public boolean ajouterReservation(Reservation reservation) {
        String sql = "INSERT INTO Reservation (id_client, id_seance, nombre_places, statut) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, reservation.getIdClient());
            stmt.setInt(2, reservation.getIdSeance());
            stmt.setInt(3, reservation.getNombrePlaces());
            stmt.setString(4, reservation.getStatut());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

 // --- LISTER LES BILLETS D'UN CLIENT SPÉCIFIQUE ---
    public List<Reservation> listerMesTickets(int idClient) {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT r.id_reservation, r.id_client, r.id_seance, r.date_reservation, r.nombre_places, r.statut, " +
                     "c.nom || ' ' || c.prenom AS nom_client, " +
                     "f.titre AS titre_film, " +
                     "(s.date_projection + s.heure_debut) AS date_seance " + 
                     "FROM Reservation r " +
                     "INNER JOIN Client c ON r.id_client = c.id_client " +
                     "INNER JOIN Seance s ON r.id_seance = s.id_seance " +
                     "INNER JOIN Film f ON s.id_film = f.id_film " +
                     "WHERE r.id_client = ? " + // FILTRE SUR LE CLIENT CONNECTÉ
                     "ORDER BY s.date_projection DESC"; 

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idClient);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    reservations.add(new Reservation(
                        rs.getInt("id_reservation"),
                        rs.getInt("id_client"),
                        rs.getInt("id_seance"),
                        rs.getTimestamp("date_reservation").toLocalDateTime(),
                        rs.getInt("nombre_places"),
                        rs.getString("statut"),
                        rs.getString("nom_client"),
                        rs.getString("titre_film"),
                        rs.getTimestamp("date_seance").toLocalDateTime()
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    // --- NOUVEAU : LISTER TOUTES LES RÉSERVATIONS (Vue Admin) ---
    public List<Reservation> listerReservations() {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT r.id_reservation, r.id_client, r.id_seance, r.date_reservation, r.nombre_places, r.statut, " +
                     "c.nom || ' ' || c.prenom AS nom_client, " +
                     "f.titre AS titre_film, " +
                     "(s.date_projection + s.heure_debut) AS date_seance " + 
                     "FROM Reservation r " +
                     "INNER JOIN Client c ON r.id_client = c.id_client " +
                     "INNER JOIN Seance s ON r.id_seance = s.id_seance " +
                     "INNER JOIN Film f ON s.id_film = f.id_film " +
                     "ORDER BY r.date_reservation DESC"; 

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                reservations.add(new Reservation(
                    rs.getInt("id_reservation"),
                    rs.getInt("id_client"),
                    rs.getInt("id_seance"),
                    rs.getTimestamp("date_reservation").toLocalDateTime(),
                    rs.getInt("nombre_places"),
                    rs.getString("statut"),
                    rs.getString("nom_client"),
                    rs.getString("titre_film"),
                    rs.getTimestamp("date_seance").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    // --- NOUVEAU : ANNULER/SUPPRIMER UNE RÉSERVATION ---
    public boolean supprimerReservation(int idReservation) {
        String sql = "DELETE FROM Reservation WHERE id_reservation = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idReservation);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}