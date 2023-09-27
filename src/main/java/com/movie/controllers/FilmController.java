package com.movie.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.movie.models.Rate;
import com.movie.models.User;
import com.movie.services.FilmService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.movie.controllers.assemblers.FilmModelAssembler;
import com.movie.controllers.exceptions.FilmNotFoundException;
import com.movie.models.Film;
import com.movie.repositories.FilmRepository;

@RestController
public class FilmController {

	//private final FilmRepository repository;
	private final FilmModelAssembler assembler;
	private final FilmService filmService;
	// In-memory storage for films ;Using ConcurrentHashMap ensures safe concurrent access, making it a preferred choice for multithreaded applications, like web applications developed using Spring Boot.
	private final Map<Long, Film> inMemoryFilms = new ConcurrentHashMap<>();
	public FilmController(FilmService filmService, FilmModelAssembler assembler) {
		this.filmService=filmService;
		this.assembler=assembler;
	}



	// Aggregate root
	// tag::get-aggregate-root[]
	@GetMapping("/films")
	public	CollectionModel<EntityModel<Film>> all() {
		List<Film> allFilms = filmService.getAllFilms();
		//calculating average rating
		allFilms.forEach(film->{
				film.setAverageRating(film.getRate().stream().mapToDouble(Rate::getRating).average().orElse(Double.NaN));
				inMemoryFilms.put(film.getFilmId(), film);
	});
		//wrapping model
		List<EntityModel<Film>> films = allFilms.stream().map(assembler::toModel)
				.collect(Collectors.toList());
			  return CollectionModel.of(films, linkTo(methodOn(FilmController.class).all()).withSelfRel());
	}
	// end::get-aggregate-root[]
	
	@PostMapping("/films")
	ResponseEntity<?> newFilm(@RequestBody Film newFilm) {
		EntityModel<Film> entityModel=assembler.toModel( filmService.saveFilm(newFilm));
		return  ResponseEntity //Additionally, return the model-based version of the saved object.
	      .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
	      .body(entityModel);
	}

	// Single item
	@GetMapping("/films/{id}")
	public	EntityModel<Film> one(@PathVariable Long id) {
		Film film = filmService.getFilmById(id) //
		      .orElseThrow(() -> new FilmNotFoundException(id));
		Double averageRating=film.getRate().stream().mapToDouble(Rate::getRating).average().orElse(Double.NaN);
		film.setAverageRating(averageRating);
		return assembler.toModel(film);
		
	}

	@PutMapping("/films/{id}")
	ResponseEntity<?> replaceFilm(@RequestBody Film newFilm, @PathVariable Long id) {
		Film updatedFilm = filmService.getFilmById(id)
		.map(film -> {
			film.setTitle(newFilm.getTitle());
			film.setDirector(newFilm.getDirector());
			film.setGenre(newFilm.getGenre());
			film.setAverageRating(Double.NaN);
			return filmService.updateFilm(film);
		}).orElseGet(() -> {
			newFilm.setFilmId(id);
			return filmService.saveFilm(newFilm);
		});
		
		EntityModel<Film> entityModel = assembler.toModel(updatedFilm);
		return ResponseEntity //
			      .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
			      .body(entityModel);
	}

	@DeleteMapping("/films/{id}")
	ResponseEntity<?> deleteFilm(@PathVariable Long id) {
		filmService.deleteFilm(id);
		//Returns a 204 No Content status, which is semantically appropriate for a DELETE
		return ResponseEntity.noContent().build();
	}
	//TODO Add suggestion mechanism
	@GetMapping("/film/recommendations")
	public	CollectionModel<EntityModel<Film>> recommendations() {
		//getting current user
		UserDetails userDetails =
				(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User currentUser = (User)userDetails;

		List<EntityModel<Film>> films = inMemoryFilms.values().stream()
				//first criteria is avg rating >2.5
				.filter(film->film.getAverageRating()>=2.5)
				//TODO second criteria is current user has rated a similar genre
				.map(assembler::toModel)
				.collect(Collectors.toList());

		return CollectionModel.of(films, linkTo(methodOn(FilmController.class).all()).withSelfRel());
	}

	//TODO Add pagination for big data load

}
