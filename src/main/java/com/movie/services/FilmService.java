package com.movie.services;


import com.movie.models.Film;

import java.util.List;
import java.util.Optional;

public interface FilmService {
    Film saveFilm(Film film);
    List<Film> getAllFilms();
    Optional<Film> getFilmById(long id);
    Film updateFilm(Film updatedFilm);
    void deleteFilm(long id);
}
