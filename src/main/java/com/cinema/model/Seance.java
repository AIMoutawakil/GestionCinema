package com.cinema.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Seance {
    private int idSeance;
    private int idFilm;   
    private int idSalle;
    private LocalDate dateProjection;
    private LocalTime heureDebut;
    private double tarif;
    private String titreFilm;
    private String nomSalle;

    public Seance() {}


    public Seance(int idFilm, int idSalle, LocalDate dateProjection, LocalTime heureDebut, double tarif) {
        this.idFilm = idFilm;
        this.idSalle = idSalle;
        this.dateProjection = dateProjection;
        this.heureDebut = heureDebut;
        this.tarif = tarif;
    }

    public Seance(int idSeance, int idFilm, int idSalle, LocalDate dateProjection, LocalTime heureDebut, double tarif, String titreFilm, String nomSalle) {
        this.idSeance = idSeance;
        this.idFilm = idFilm;
        this.idSalle = idSalle;
        this.dateProjection = dateProjection;
        this.heureDebut = heureDebut;
        this.tarif = tarif;
        this.titreFilm = titreFilm;
        this.nomSalle = nomSalle;
    }

    public int getIdSeance() { return idSeance; }
    public void setIdSeance(int idSeance) { this.idSeance = idSeance; }

    public int getIdFilm() { return idFilm; }
    public void setIdFilm(int idFilm) { this.idFilm = idFilm; }

    public int getIdSalle() { return idSalle; }
    public void setIdSalle(int idSalle) { this.idSalle = idSalle; }

    public LocalDate getDateProjection() { return dateProjection; }
    public void setDateProjection(LocalDate dateProjection) { this.dateProjection = dateProjection; }

    public LocalTime getHeureDebut() { return heureDebut; }
    public void setHeureDebut(LocalTime heureDebut) { this.heureDebut = heureDebut; }

    public double getTarif() { return tarif; }
    public void setTarif(double tarif) { this.tarif = tarif; }

    public String getTitreFilm() { return titreFilm; }
    public void setTitreFilm(String titreFilm) { this.titreFilm = titreFilm; }

    public String getNomSalle() { return nomSalle; }
    public void setNomSalle(String nomSalle) { this.nomSalle = nomSalle; }
}
