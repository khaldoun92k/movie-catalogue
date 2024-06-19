package com.movie.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movie.models.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> { //<Domain(entity),Id type>
	Optional<User> findByUsername(String username);
}
