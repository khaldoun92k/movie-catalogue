package com.movie.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movie.models.Film;
import com.movie.models.Rate;
import com.movie.models.keys.RateId;

public interface RateRepository extends JpaRepository<Rate, RateId> { //<Domain(entity),Id type>

}
