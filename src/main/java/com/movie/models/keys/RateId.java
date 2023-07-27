package com.movie.models.keys;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

//and then of course IdClass, because combined key:
@Embeddable
public class RateId implements Serializable {

	@Column(name = "userId", nullable = false)
	private Long user;

	@Column(name = "filmId", nullable = false)
	private Long film;

	public RateId() {
	}

	public RateId(Long user, Long film) {

		this.user = user;
		this.film = film;
	}

	public Long getUser() {
		return user;
	}

	public void setUser(Long user) {
		this.user = user;
	}

	public Long getFilm() {
		return film;
	}

	public void setFilm(Long film) {
		this.film = film;
	}

	@Override
	public int hashCode() {
		return Objects.hash(film, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RateId other = (RateId) obj;
		return Objects.equals(film, other.film) && Objects.equals(user, other.user);
	}

	@Override
	public String toString() {
		return "RateId [user=" + user + ", film=" + film + "]";
	}

}