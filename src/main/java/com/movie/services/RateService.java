package com.movie.services;

import com.movie.models.Film;
import com.movie.models.Rate;
import com.movie.models.User;
import com.movie.models.keys.RateId;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;


public interface RateService {
	Rate saveRate(Rate rate);
	List<Rate> getAllRates();
	Optional<Rate> getRateById(RateId id);
	Rate updateRate(Rate updateRate);
	void deleteRate(RateId id);
}
