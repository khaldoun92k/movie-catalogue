package com.movie.controllers.exceptions;

import com.movie.models.keys.RateId;

public class RateNotFoundException extends RuntimeException {

	public RateNotFoundException(RateId id) {
		super("Could not find rate "+id);
	}
}
