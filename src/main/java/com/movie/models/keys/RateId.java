package com.movie.models.keys;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

//and then of course IdClass, because combined key:
@Embeddable
public class RateId implements Serializable{
@Column(name="userId")
private Long user;

@Column(name="filmId")
private Long course;
}