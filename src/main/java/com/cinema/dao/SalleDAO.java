package com.cinema.dao;

import com.cinema.model.Salle;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalleDAO {
    private Connection connection;

    public SalleDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    // --- AJOUTER UNE SALLE (Create) ---
    public boolean ajouterSalle(Salle salle) {
        String sql = "INSERT INTO Salle (nom_salle, capacite, technologie) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, salle.getNomSalle());
            stmt.setInt(2, salle.getCapacite());
            stmt.setString(3, salle.getTechnologie());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // --- LISTER LES SALLES (Read) ---
    public List<Salle> listerSalles() {
        List<Salle> salles = new ArrayList<>();
        String sql = "SELECT * FROM Salle";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                salles.add(new Salle(
                    rs.getInt("id_salle"),
                    rs.getString("nom_salle"),
                    rs.getInt("capacite"),
                    rs.getString("technologie")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salles;
    }

    // --- MODIFIER UNE SALLE (Update) - NOUVEAU ---
    public boolean modifierSalle(Salle salle) {
        String sql = "UPDATE Salle SET nom_salle = ?, capacite = ?, technologie = ? WHERE id_salle = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, salle.getNomSalle());
            stmt.setInt(2, salle.getCapacite());
            stmt.setString(3, salle.getTechnologie());
            stmt.setInt(4, salle.getIdSalle());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // --- SUPPRIMER UNE SALLE (Delete) - NOUVEAU ---
    public boolean supprimerSalle(int idSalle) {
        String sql = "DELETE FROM Salle WHERE id_salle = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idSalle);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}