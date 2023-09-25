package com.movie.models;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
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
	private Double averageRating;
	@JsonIgnore
	@OneToMany(mappedBy = "film", cascade = CascadeType.ALL)
    private Set<Rate> rate=new HashSet<>();
	
	public Film() {
	}

	public Film(String title, String genre, String director) {
		this.title = title;
		this.genre = genre;
		this.director = director;
		this.averageRating = Double.NaN;
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

	public Double getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(Double averageRating) {
		this.averageRating = averageRating;
	}

	public Set<Rate> getRate() {
		return rate;
	}

	public void setRate(Set<Rate> rate) {
		this.rate = rate;
	}

	@Override
	public String toString() {
		return "Film{" +
				"filmId=" + filmId +
				", title='" + title + '\'' +
				", genre='" + genre + '\'' +
				", director='" + director + '\'' +
				", AverageRating=" + averageRating +
				'}';
	}
}
