package com.movie.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movie.models.User;

public interface UserRepository extends JpaRepository<User, Long> { //<Domain(entity),Id type>

}
