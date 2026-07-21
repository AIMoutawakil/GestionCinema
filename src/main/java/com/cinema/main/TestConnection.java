package com.cinema.main;

import com.cinema.dao.DatabaseConnection;
import java.sql.Connection;

public class TestConnection {
    
    public static void main(String[] args) {
        System.out.println("Lancement du test de connexion à PostgreSQL...");
        
        Connection conn = DatabaseConnection.getConnection();
        
        if (conn != null) {
            System.out.println("✅ TEST RÉUSSI : La communication avec la base de données fonctionne parfaitement !");
        } else {
            System.out.println("❌ ÉCHEC DU TEST : Vérifie l'URL, l'utilisateur, le mot de passe, ou si le serveur PostgreSQL est bien allumé.");
        }
    }
}

