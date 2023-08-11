package com.movie;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class MovieCatalogueApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieCatalogueApplication.class, args);
	}
	
	
	
	//TODO Resource provider need to be handled
	//To service the authenticate() function we need to add a new endpoint to the backend
	 @RequestMapping("/users")
	  public Principal user(Principal user) {
	    return user;
	  }
	//Adding Dynamic Content to angular
	  @RequestMapping("/resource")
	  public Map<String,Object> home() {
	    Map<String,Object> model = new HashMap<String,Object>();
	    model.put("id", UUID.randomUUID().toString());
	    model.put("content", "Hello World");
	    return model;
	  }

}
