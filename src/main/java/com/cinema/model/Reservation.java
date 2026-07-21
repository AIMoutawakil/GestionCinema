package com.cinema.model;

import java.time.LocalDateTime;

public class Reservation {
    private int idReservation;
    private int idClient;  
    private int idSeance;  
    private LocalDateTime dateReservation;
    private int nombrePlaces;
    private String statut; 

    // --- ATTRIBUTS SUPPLÉMENTAIRES POUR L'AFFICHAGE (Admin) ---
    private String nomClient;
    private String titreFilm;
    private LocalDateTime dateSeance;

    public Reservation() {}

    // Constructeur pour l'insertion (Client)
    public Reservation(int idClient, int idSeance, int nombrePlaces, String statut) {
        this.idClient = idClient;
        this.idSeance = idSeance;
        this.nombrePlaces = nombrePlaces;
        this.statut = statut;
    }

    // Constructeur basique (Base de données)
    public Reservation(int idReservation, int idClient, int idSeance, LocalDateTime dateReservation, int nombrePlaces, String statut) {
        this.idReservation = idReservation;
        this.idClient = idClient;
        this.idSeance = idSeance;
        this.dateReservation = dateReservation;
        this.nombrePlaces = nombrePlaces;
        this.statut = statut;
    }

    // NOUVEAU : Constructeur complet avec les jointures pour l'affichage (Admin)
    public Reservation(int idReservation, int idClient, int idSeance, LocalDateTime dateReservation, int nombrePlaces, String statut, String nomClient, String titreFilm, LocalDateTime dateSeance) {
        this.idReservation = idReservation;
        this.idClient = idClient;
        this.idSeance = idSeance;
        this.dateReservation = dateReservation;
        this.nombrePlaces = nombrePlaces;
        this.statut = statut;
        this.nomClient = nomClient;
        this.titreFilm = titreFilm;
        this.dateSeance = dateSeance;
    }

    // --- GETTERS & SETTERS EXISTANTS ---
    public int getIdReservation() { return idReservation; }
    public void setIdReservation(int idReservation) { this.idReservation = idReservation; }

    public int getIdClient() { return idClient; }
    public void setIdClient(int idClient) { this.idClient = idClient; }

    public int getIdSeance() { return idSeance; }
    public void setIdSeance(int idSeance) { this.idSeance = idSeance; }

    public LocalDateTime getDateReservation() { return dateReservation; }
    public void setDateReservation(LocalDateTime dateReservation) { this.dateReservation = dateReservation; }

    public int getNombrePlaces() { return nombrePlaces; }
    public void setNombrePlaces(int nombrePlaces) { this.nombrePlaces = nombrePlaces; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    // --- NOUVEAUX GETTERS & SETTERS POUR L'AFFICHAGE ---
    public String getNomClient() { return nomClient; }
    public void setNomClient(String nomClient) { this.nomClient = nomClient; }

    public String getTitreFilm() { return titreFilm; }
    public void setTitreFilm(String titreFilm) { this.titreFilm = titreFilm; }

    public LocalDateTime getDateSeance() { return dateSeance; }
    public void setDateSeance(LocalDateTime dateSeance) { this.dateSeance = dateSeance; }
}