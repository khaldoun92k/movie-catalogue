package com.movie.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import com.movie.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.movie.controllers.assemblers.UserModelAssembler;
import com.movie.controllers.exceptions.UserNotFoundException;
import com.movie.models.User;


@RestController
public class UserController {

	private final UserServiceImpl userService;
	private final UserModelAssembler assembler;

	public UserController(UserServiceImpl userService, UserModelAssembler assembler) {
		this.userService = userService;
		this.assembler = assembler;
	}

	// Aggregate root
	// tag::get-aggregate-root[]
	@GetMapping("/users")
	public	CollectionModel<EntityModel<User>> all() {
		List<EntityModel<User>> users = userService.getAllUsers().stream()
			      .map(assembler::toModel)
			      .collect(Collectors.toList());

			  return CollectionModel.of(users, linkTo(methodOn(UserController.class).all()).withSelfRel());
	}
	// end::get-aggregate-root[]

	@PostMapping("/users")
	ResponseEntity<?> newUser(@RequestBody User newUser) {
		EntityModel<User> entityModel=assembler.toModel( userService.saveUser(newUser));
		return  ResponseEntity //Additionally, return the model-based version of the saved object.
	      .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
	      .body(entityModel);
	}

	// Single item
	@GetMapping("/users/{id}")
	public	EntityModel<User> one(@PathVariable Long id) {


		User user = userService.getUserById(id) //
		      .orElseThrow(() -> new UserNotFoundException(id));

		  return assembler.toModel(user);
		
	}

	@PutMapping("/users/{id}")
	ResponseEntity<?> replaceUser(@RequestBody User newUser, @PathVariable Long id) {

		User updatedUser=userService.getUserById(id).map(user -> {
			user.setUsername(newUser.getUsername());
			user.setPassword(newUser.getPassword());
			return userService.updateUser(user);
		}).orElseGet(() -> {
			newUser.setUserId(id);
			return userService.updateUser(newUser);
		});
		EntityModel<User> entityModel = assembler.toModel(updatedUser);
		return ResponseEntity //
			      .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
			      .body(entityModel);
	}

	@DeleteMapping("/users/{id}")
	ResponseEntity<?> deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		return ResponseEntity.noContent().build();
	}
	

	
}
