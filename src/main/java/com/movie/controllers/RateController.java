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

import com.movie.controllers.assemblers.RateModelAssembler;
import com.movie.controllers.exceptions.RateNotFoundException;
import com.movie.models.Film;
import com.movie.models.Rate;
import com.movie.models.User;
import com.movie.models.keys.RateId;
import com.movie.repositories.RateRepository;

@RestController
public class RateController {

	private final RateRepository repository;
	private final RateModelAssembler assembler;
	RateController(RateRepository repository,RateModelAssembler assembler) {
		this.repository = repository;
		this.assembler=assembler;
	}

	// Aggregate root
	// tag::get-aggregate-root[]
	@GetMapping("/rates")
	public	CollectionModel<EntityModel<Rate>> all() {
		List<EntityModel<Rate>> rates = repository.findAll().stream()
			      .map(assembler::toModel)
			      .collect(Collectors.toList());

			  return CollectionModel.of(rates, linkTo(methodOn(RateController.class).all()).withSelfRel());
	}
	// end::get-aggregate-root[]
	
	@PostMapping("/rates")
	ResponseEntity<?> newRate(@RequestBody Rate newRate) {	
	
		EntityModel<Rate> entityModel=assembler.toModel( repository.save(newRate));
		return  ResponseEntity //Additionally, return the model-based version of the saved object.
	      .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
	      .body(entityModel);
	}

	// Single item
	@GetMapping("/rates/{userId}/{filmId}")
	public	EntityModel<Rate> one(@PathVariable("userId") Long userId,@PathVariable("filmId") Long filmId) {

		RateId rateId = new RateId(userId, filmId);
		Rate rate = repository.findById(rateId) //
		      .orElseThrow(() -> new RateNotFoundException(rateId));

		  return assembler.toModel(rate);
		
	}

	@PutMapping("/rates/{userId}/{filmId}")
	ResponseEntity<?> replaceRate(@RequestBody Rate newRate, @PathVariable("userId") Long userId,@PathVariable("filmId") Long filmId) {

		RateId rateId = new RateId(userId, filmId);
		Rate updatedRate = repository.findById(rateId)
		.map(rate -> {
			rate.setRating(newRate.getRating());
			return repository.save(rate);
		}).orElseGet(() -> {
			newRate.setRateId(rateId);
			return repository.save(newRate);
		});
		
		EntityModel<Rate> entityModel = assembler.toModel(updatedRate);
		return ResponseEntity //
			      .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
			      .body(entityModel);
	}

	@DeleteMapping("/rates/{userId}/{filmId}")
	ResponseEntity<?> deleteRate( @PathVariable("userId") Long userId,@PathVariable("filmId") Long filmId) {
		RateId rateId = new RateId(userId, filmId);
		repository.deleteById(rateId);
		return ResponseEntity.noContent().build();
	}
}
