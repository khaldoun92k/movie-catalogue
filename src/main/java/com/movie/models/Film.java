package com.movie.models;

import java.util.Collection;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Film {

	@Id
	@GeneratedValue
	private Long filmId;
	private String title;
	private String genre;
	private String director;

	@OneToMany(mappedBy = "film")
    private Collection<Rate> rate;
	
	public Film() {
	}

	public Film(String title, String genre, String director) {
		this.title = title;
		this.genre = genre;
		this.director = director;
	}

	public Long getFilmId() {
		return filmId;
	}

	public void setFilmId(Long filmId) {
		this.filmId = filmId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	@Override
	public String toString() {
		return "Film [filmId=" + filmId + ", title=" + title + ", genre=" + genre + ", director=" + director + "]";
	}

}
