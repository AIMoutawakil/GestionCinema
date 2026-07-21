package com.cinema.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    
    // Remplace par tes propres identifiants PostgreSQL
    private static final String URL = "jdbc:postgresql://localhost:5432/Gestion_Cinema";
    private static final String USER = "postgres"; 
    private static final String PASSWORD = "1234"; 

    private static Connection connection = null;

    // Constructeur privé pour empêcher l'instanciation (Singleton)
    private DatabaseConnection() {}

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connexion à la base de données réussie !");
            } catch (SQLException e) {
                System.err.println("Erreur de connexion à la base de données !");
                e.printStackTrace();
            }
        }
        return connection;
    }
}