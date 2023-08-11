package com.movie;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.movie.models.Film;
import com.movie.models.User;
import com.movie.repositories.FilmRepository;
import com.movie.repositories.UserRepository;

@Configuration
public class LoadDatabase {
	 private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
	 
	 private static final String PRELOADING="Preloading";
	 
	  @Bean
	  CommandLineRunner initDatabase(UserRepository userRepository,FilmRepository filmRepository) {

	    return args -> {
	      log.info(PRELOADING+"{}",userRepository.save(new User("user1","pwd1")));
	      log.info(PRELOADING+"{}",userRepository.save(new User("user2","pwd2")));
	      
	      log.info(PRELOADING+"{}",filmRepository.save(new Film("film1", "genre1", "director1")));
	      log.info(PRELOADING+"{}",filmRepository.save(new Film("film2", "genre2", "director2")));
	    };
	  }
}
