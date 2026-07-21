package com.cinema.model;

public class Admin {
    private int idAdmin;
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;

    // Constructeur vide
    public Admin() {}

    // Constructeur sans ID (pour l'insertion en base)
    public Admin(String nom, String prenom, String email, String motDePasse) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
    }

    // Constructeur avec ID (pour la récupération depuis la base)
    public Admin(int idAdmin, String nom, String prenom, String email, String motDePasse) {
        this.idAdmin = idAdmin;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
    }

	public int getIdAdmin() {
		return idAdmin;
	}

	public void setIdAdmin(int idAdmin) {
		this.idAdmin = idAdmin;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

}