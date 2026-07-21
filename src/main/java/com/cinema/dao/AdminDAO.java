package com.cinema.dao;

import com.cinema.model.Admin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAO {
    private Connection connection;

    public AdminDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public boolean ajouterAdmin(Admin admin) {
        String sql = "INSERT INTO Admin (nom, prenom, email, mot_de_passe) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, admin.getNom());
            stmt.setString(2, admin.getPrenom());
            stmt.setString(3, admin.getEmail());
            stmt.setString(4, admin.getMotDePasse()); // Dans un projet final, ce mot de passe devra être hashé
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Admin authentifierAdmin(String email, String motDePasse) {
        String sql = "SELECT * FROM Admin WHERE email = ? AND mot_de_passe = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, motDePasse);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Admin(
                        rs.getInt("id_admin"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("mot_de_passe")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}