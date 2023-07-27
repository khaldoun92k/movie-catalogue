package com.movie.models;



import org.springframework.validation.annotation.Validated;

import com.movie.models.keys.RateId;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
@Validated
public class Rate {
	
	@EmbeddedId
	RateId rateId;
	
	@ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "userId")
    private User user;
	
    
    @ManyToOne
    @MapsId("filmId")
    @JoinColumn(name = "filmId")
    private Film film;
    
    @Min(value = 1 , message = "Value should must be higher or equal then 1")
    @Max(value = 5 , message = "Value should must be lower or equal then 5")
    private Long rating;



	public Rate() {
	}

	public Rate(User user, Film film, Long rating,RateId rateId) {
		this.rateId = rateId;
		this.user = user;
		this.film = film;
		this.rating = rating;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Film getFilm() {
		return film;
	}

	public void setFilm(Film film) {
		this.film = film;
	}

	public Long getRating() {
		return rating;
	}

	public void setRating(Long rating) {
		this.rating = rating;
	}

	public RateId getRateId() {
		return rateId;
	}

	public void setRateId(RateId rateId) {
		this.rateId = rateId;
	}

	@Override
	public String toString() {
		return "Rate [rateId=" + rateId + ", user=" + user + ", film=" + film + ", rating=" + rating + "]";
	}


	
}

