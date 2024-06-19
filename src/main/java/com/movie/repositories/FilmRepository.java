package com.movie.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movie.models.Film;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> { //<Domain(entity),Id type>

}
