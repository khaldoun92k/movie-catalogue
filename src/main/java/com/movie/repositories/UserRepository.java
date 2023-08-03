package com.movie.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movie.models.User;

public interface UserRepository extends JpaRepository<User, Long> { //<Domain(entity),Id type>
	Optional<User> findByUserName(String userName);
}
