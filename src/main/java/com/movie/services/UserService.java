package com.movie.services;

import com.movie.models.Film;
import org.springframework.security.core.userdetails.UserDetails;

import com.movie.models.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
	User saveUser(User user);
	List<User> getAllUsers();
	Optional<User> getUserById(long id);
	User updateUser(User updatedUser);
	void deleteUser(long id);
	UserDetails loadUserByUsername(String username);
	void register(User user);
	boolean checkIfUserExist(String username);
    void deleteAllUsers();
}
