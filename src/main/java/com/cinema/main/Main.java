package com.cinema.main;

import com.cinema.view.WelcomeView; // On importe la nouvelle vue
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // ON LANCE LA VUE D'ACCUEIL MAINTENANT
        WelcomeView welcomeView = new WelcomeView(primaryStage);
        
        Scene scene = new Scene(welcomeView.getView(), 600, 500); // Fenêtre un peu plus grande pour l'image
        
        String css = this.getClass().getResource("/css/style.css").toExternalForm();
        scene.getStylesheets().add(css);
        
        primaryStage.setTitle("Gestion de Cinéma - Accueil");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true); 
        primaryStage.setMinWidth(500);
        primaryStage.setMinHeight(400);
        
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}