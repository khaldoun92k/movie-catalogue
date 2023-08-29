package com.movie.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
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
	FilmController(FilmRepository repository,FilmModelAssembler assembler) {
		this.repository = repository;
		this.assembler=assembler;
	}

	// Aggregate root
	// tag::get-aggregate-root[]
	@GetMapping("/films")
	public	CollectionModel<EntityModel<Film>> all() {
		List<EntityModel<Film>> films = repository.findAll().stream()
			      .map(assembler::toModel)
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

		  return assembler.toModel(film);
		
	}

	@PutMapping("/films/{id}")
	ResponseEntity<?> replaceFilm(@RequestBody Film newFilm, @PathVariable Long id) {

		Film updatedFilm = repository.findById(id)
		.map(film -> {
			film.setTitle(newFilm.getTitle());
			film.setDirector(newFilm.getDirector());
			film.setGenre(newFilm.getGenre());
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
	//TODO Add pagination for big data load
	//TODO Add suggestion mechanism
}
