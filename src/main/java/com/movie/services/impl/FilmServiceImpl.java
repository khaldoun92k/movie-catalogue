package com.movie.services.impl;

import com.movie.models.Film;
import com.movie.repositories.FilmRepository;
import com.movie.services.FilmService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FilmServiceImpl implements FilmService {
    private final FilmRepository filmRepository;

    public FilmServiceImpl(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @Override
    public Film saveFilm(Film film) {
        return filmRepository.save(film);
    }

    @Override
    public List<Film> getAllFilms() {
        return filmRepository.findAll();
    }

    @Override
    public Optional<Film> getFilmById(long id) {
        return filmRepository.findById(id);
    }

    @Override
    public Film updateFilm(Film updatedFilm) {
        return filmRepository.save(updatedFilm);
    }

    @Override
    public void deleteFilm(long id) {
        filmRepository.deleteById(id);
    }
}
