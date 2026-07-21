package com.cinema.dao;

import com.cinema.model.Film;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FilmDAO {

    private Connection connection;

    public FilmDAO() {
        // On récupère la connexion unique créée à l'étape 3
        this.connection = DatabaseConnection.getConnection();
    }

    // 1. Ajouter un nouveau film (Create)
    public boolean ajouterFilm(Film film) {
        String sql = "INSERT INTO Film (titre, genre, duree_minutes, realisateur, synopsis, chemin_affiche) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, film.getTitre());
            stmt.setString(2, film.getGenre());
            stmt.setInt(3, film.getDureeMinutes());
            stmt.setString(4, film.getRealisateur());
            stmt.setString(5, film.getSynopsis());
            stmt.setString(6, film.getCheminAffiche());
            
            int lignesAffectees = stmt.executeUpdate();
            return lignesAffectees > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 2. Récupérer tous les films (Read)
    public List<Film> listerFilms() {
        List<Film> films = new ArrayList<>();
        String sql = "SELECT * FROM Film";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Film film = new Film(
                    rs.getInt("id_film"),
                    rs.getString("titre"),
                    rs.getString("genre"),
                    rs.getInt("duree_minutes"),
                    rs.getString("realisateur"),
                    rs.getString("synopsis"),
                    rs.getString("chemin_affiche")
                );
                films.add(film);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return films;
    }

    // 3. Mettre à jour un film existant (Update)
    public boolean modifierFilm(Film film) {
        String sql = "UPDATE Film SET titre = ?, genre = ?, duree_minutes = ?, realisateur = ?, synopsis = ?, chemin_affiche = ? WHERE id_film = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, film.getTitre());
            stmt.setString(2, film.getGenre());
            stmt.setInt(3, film.getDureeMinutes());
            stmt.setString(4, film.getRealisateur());
            stmt.setString(5, film.getSynopsis());
            stmt.setString(6, film.getCheminAffiche());
            stmt.setInt(7, film.getIdFilm());
            
            int lignesAffectees = stmt.executeUpdate();
            return lignesAffectees > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 4. Supprimer un film (Delete)
    public boolean supprimerFilm(int idFilm) {
        String sql = "DELETE FROM Film WHERE id_film = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idFilm);
            int lignesAffectees = stmt.executeUpdate();
            return lignesAffectees > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}