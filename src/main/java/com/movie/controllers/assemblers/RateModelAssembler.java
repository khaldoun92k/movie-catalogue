package com.movie.controllers.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.movie.controllers.RateController;
import com.movie.models.Rate;

@Component
public class RateModelAssembler implements RepresentationModelAssembler<Rate, EntityModel<Rate>> {

  @Override
  public EntityModel<Rate> toModel(Rate rate) {

    return EntityModel.of(rate, //
        linkTo(methodOn(RateController.class).one(rate.getRateId().getUser(),rate.getRateId().getFilm())).withSelfRel(), // add hypertext links to make it Restful
        linkTo(methodOn(RateController.class).all()).withRel("rates"));
  }
}
