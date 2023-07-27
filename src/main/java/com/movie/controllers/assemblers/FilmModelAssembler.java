package com.movie.controllers.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.movie.controllers.FilmController;
import com.movie.models.Film;

@Component
public class FilmModelAssembler implements RepresentationModelAssembler<Film, EntityModel<Film>> {

  @Override
  public EntityModel<Film> toModel(Film film) {

    return EntityModel.of(film, //
        linkTo(methodOn(FilmController.class).one(film.getFilmId())).withSelfRel(), // add hypertext links to make it Restful
        linkTo(methodOn(FilmController.class).all()).withRel("films"));
  }
}
