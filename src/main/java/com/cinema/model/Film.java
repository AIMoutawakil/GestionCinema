package com.cinema.model;

public class Film {
    private int idFilm;
    private String titre;
    private String genre;
    private int dureeMinutes;
    private String realisateur;
    private String synopsis;
    private String cheminAffiche;

    public Film() {}

    public Film(String titre, String genre, int dureeMinutes, String realisateur, String synopsis, String cheminAffiche) {
        this.titre = titre;
        this.genre = genre;
        this.dureeMinutes = dureeMinutes;
        this.realisateur = realisateur;
        this.synopsis = synopsis;
        this.cheminAffiche = cheminAffiche;
    }

    public Film(int idFilm, String titre, String genre, int dureeMinutes, String realisateur, String synopsis, String cheminAffiche) {
        this.idFilm = idFilm;
        this.titre = titre;
        this.genre = genre;
        this.dureeMinutes = dureeMinutes;
        this.realisateur = realisateur;
        this.synopsis = synopsis;
        this.cheminAffiche = cheminAffiche;
    }

	public int getIdFilm() {
		return idFilm;
	}

	public void setIdFilm(int idFilm) {
		this.idFilm = idFilm;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public int getDureeMinutes() {
		return dureeMinutes;
	}

	public void setDureeMinutes(int dureeMinutes) {
		this.dureeMinutes = dureeMinutes;
	}

	public String getRealisateur() {
		return realisateur;
	}

	public void setRealisateur(String realisateur) {
		this.realisateur = realisateur;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public String getCheminAffiche() {
		return cheminAffiche;
	}

	public void setCheminAffiche(String cheminAffiche) {
		this.cheminAffiche = cheminAffiche;
	}
}