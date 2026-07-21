package com.cinema.model;

public class Salle {
    private int idSalle;
    private String nomSalle;
    private int capacite;
    private String technologie;

    public Salle() {}

    public Salle(String nomSalle, int capacite, String technologie) {
        this.nomSalle = nomSalle;
        this.capacite = capacite;
        this.technologie = technologie;
    }

    public Salle(int idSalle, String nomSalle, int capacite, String technologie) {
        this.idSalle = idSalle;
        this.nomSalle = nomSalle;
        this.capacite = capacite;
        this.technologie = technologie;
    }

	public int getIdSalle() {
		return idSalle;
	}

	public void setIdSalle(int idSalle) {
		this.idSalle = idSalle;
	}

	public String getNomSalle() {
		return nomSalle;
	}

	public void setNomSalle(String nomSalle) {
		this.nomSalle = nomSalle;
	}

	public int getCapacite() {
		return capacite;
	}

	public void setCapacite(int capacite) {
		this.capacite = capacite;
	}

	public String getTechnologie() {
		return technologie;
	}

	public void setTechnologie(String technologie) {
		this.technologie = technologie;
	}
}