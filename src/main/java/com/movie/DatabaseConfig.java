package com.movie;

import com.movie.models.Film;
import com.movie.models.User;
import com.movie.repositories.FilmRepository;
import com.movie.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {

    private static final String PRELOADING="Preloading";
    private static final Logger log = LoggerFactory.getLogger(DatabaseConfig.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FilmRepository filmRepository;

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            resetDatabase();
        };
    }

    @Transactional
    public void resetDatabase() {
        log.info("Resetting H2-Database...");
        userRepository.deleteAll();
        log.info(PRELOADING+"{}",userRepository.save(new User("user1","pwd1")));
        log.info(PRELOADING+"{}",userRepository.save(new User("user2","pwd2")));

        log.info(PRELOADING+"{}",filmRepository.save(new Film("film1", "genre1", "director1")));
        log.info(PRELOADING+"{}",filmRepository.save(new Film("film2", "genre2", "director2")));
    }
}
