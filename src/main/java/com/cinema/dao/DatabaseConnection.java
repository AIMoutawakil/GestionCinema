package com.cinema.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHandler {
    private static final String URL = System.getenv("DB_URL") != null ? 
            System.getenv("DB_URL") : "jdbc:postgresql://localhost:5432/Gestion_Hopital";

    private static final String USER = System.getenv("DB_USER") != null ? 
            System.getenv("DB_USER") : "postgres"; 

    private static final String PASSWORD = System.getenv("DB_PASSWORD") != null ? 
            System.getenv("DB_PASSWORD") : ""; 

    private static Connection connection = null;

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
