package com.movie.controllers;

import com.movie.controllers.assemblers.RateModelAssembler;
import com.movie.controllers.exceptions.RateNotFoundException;
import com.movie.models.Film;
import com.movie.models.Rate;
import com.movie.models.User;
import com.movie.models.keys.RateId;
import com.movie.services.impl.FilmServiceImpl;
import com.movie.services.impl.RateServiceImpl;
import com.movie.services.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class RateController {

	private final static Logger logger = LogManager.getLogger(RateController.class);
	private final FilmServiceImpl filmService;
	private final RateServiceImpl rateService;
	private final RateModelAssembler assembler;

	RateController(FilmServiceImpl filmService, RateServiceImpl rateService, RateModelAssembler assembler) {
		this.rateService = rateService;
		this.filmService = filmService;
		this.assembler = assembler;
	}

	//record will be used for rating requests
	private record RatingRequest (Long filmId, Long rating){}

	@CrossOrigin
    @PostMapping("/rating")
    public ResponseEntity<?> rateFilm(@RequestBody RatingRequest ratingRequest) {
		Optional<Film> filmOpt = filmService.getFilmById(ratingRequest.filmId);
        
        //getting the current user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
	        String currentUserName = auth.getName();
	        logger.info("currentUserName  {}" , currentUserName);
	    }
        var principal=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        logger.info("Principal type: {}", principal.getClass().getName());
        UserDetails userDetails =
        		 (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = (User)userDetails;
       
        if (filmOpt.isEmpty() || currentUser==null) {
            return ResponseEntity.badRequest().body("Film or User not found!");
        }

        Rate newRate = new Rate();
        newRate.setRateId(new RateId(currentUser.getUserId(), filmOpt.get().getFilmId()));
        newRate.setUser(currentUser);
        newRate.setFilm(filmOpt.get());
        newRate.setRating(ratingRequest.rating);
		rateService.saveRate(newRate);

        return ResponseEntity.ok("Film rated successfully");
    }
    
	@GetMapping("/rates")
	public	CollectionModel<EntityModel<Rate>> all() {
		List<EntityModel<Rate>> rates = rateService.getAllRates().stream()
			      .map(assembler::toModel)
			      .collect(Collectors.toList());
		return CollectionModel.of(rates, linkTo(methodOn(FilmController.class).all()).withSelfRel());
	}
	// Single item
	@GetMapping("/rates/{id}")
	public	EntityModel<Rate> one(@PathVariable RateId id) {
		Rate rate = rateService.getRateById(id) //
		      .orElseThrow(() -> new RateNotFoundException(id));
		return assembler.toModel(rate);
	}

}
