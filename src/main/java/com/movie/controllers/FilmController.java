package com.movie.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.movie.models.Rate;
import com.movie.models.User;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
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

	private final FilmRepository repository;
	private final FilmModelAssembler assembler;

	// In-memory storage for films ;Using ConcurrentHashMap ensures safe concurrent access, making it a preferred choice for multithreaded applications, like web applications developed using Spring Boot.
	private final Map<Long, Film> inMemoryFilms = new ConcurrentHashMap<>();
	FilmController(FilmRepository repository,FilmModelAssembler assembler) {
		this.repository = repository;
		this.assembler=assembler;
	}



	// Aggregate root
	// tag::get-aggregate-root[]
	@GetMapping("/films")
	public	CollectionModel<EntityModel<Film>> all() {
		List<Film> allFilms = repository.findAll();
		//calculating average rating
		allFilms.forEach(film->{
				film.setAverageRating(film.getRate().stream().mapToDouble(Rate::getRating).average().orElse(Double.NaN));
				inMemoryFilms.put(film.getFilmId(), film);
	});
		//wrapping model
		List<EntityModel<Film>> films = repository.findAll().stream().map(assembler::toModel)
				.collect(Collectors.toList());
			  return CollectionModel.of(films, linkTo(methodOn(FilmController.class).all()).withSelfRel());
	}
	// end::get-aggregate-root[]
	
	@PostMapping("/films")
	ResponseEntity<?> newFilm(@RequestBody Film newFilm) {

		EntityModel<Film> entityModel=assembler.toModel( repository.save(newFilm));
		return  ResponseEntity //Additionally, return the model-based version of the saved object.
	      .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
	      .body(entityModel);
	}

	// Single item
	@GetMapping("/films/{id}")
	public	EntityModel<Film> one(@PathVariable Long id) {


		Film film = repository.findById(id) //
		      .orElseThrow(() -> new FilmNotFoundException(id));
		Double averageRating=film.getRate().stream().mapToDouble(Rate::getRating).average().orElse(Double.NaN);
		film.setAverageRating(averageRating);
		return assembler.toModel(film);
		
	}

	@PutMapping("/films/{id}")
	ResponseEntity<?> replaceFilm(@RequestBody Film newFilm, @PathVariable Long id) {

		Film updatedFilm = repository.findById(id)
		.map(film -> {
			film.setTitle(newFilm.getTitle());
			film.setDirector(newFilm.getDirector());
			film.setGenre(newFilm.getGenre());
			film.setAverageRating(Double.NaN);
			return repository.save(film);
		}).orElseGet(() -> {
			newFilm.setFilmId(id);
			return repository.save(newFilm);
		});
		
		EntityModel<Film> entityModel = assembler.toModel(updatedFilm);
		return ResponseEntity //
			      .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
			      .body(entityModel);
	}

	@DeleteMapping("/films/{id}")
	ResponseEntity<?> deleteFilm(@PathVariable Long id) {
		repository.deleteById(id);
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
