package com.movie.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Rate {
	
	
	@Id
    @OneToMany
    @JoinColumn(name="userId")
    private User user;
    @Id
    @ManyToOne
    @JoinColumn(name="filmId")
    private Film film;
	
    private Long rating;
	
}

