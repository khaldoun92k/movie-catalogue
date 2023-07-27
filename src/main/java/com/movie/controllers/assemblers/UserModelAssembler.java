package com.movie.controllers.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import com.movie.controllers.UserController;
import com.movie.models.User;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<User, EntityModel<User>> {

  @Override
  public EntityModel<User> toModel(User user) {

    return EntityModel.of(user, //
        linkTo(methodOn(UserController.class).one(user.getUserId())).withSelfRel(), // add hypertext links to make it Restful
        linkTo(methodOn(UserController.class).all()).withRel("users"));
  }
}
