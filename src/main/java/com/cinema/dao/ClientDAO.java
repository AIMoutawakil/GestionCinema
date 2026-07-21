package com.cinema.dao;

import com.cinema.model.Client;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientDAO {
    private Connection connection;

    public ClientDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public boolean ajouterClient(Client client) {
        String sql = "INSERT INTO Client (nom, prenom, email, mot_de_passe) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, client.getNom());
            stmt.setString(2, client.getPrenom());
            stmt.setString(3, client.getEmail());
            stmt.setString(4, client.getMotDePasse());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Client authentifierClient(String email, String motDePasse) {
        String sql = "SELECT * FROM Client WHERE email = ? AND mot_de_passe = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, motDePasse);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Client(
                        rs.getInt("id_client"),
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