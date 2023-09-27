package com.movie.services;

import com.movie.models.Film;
import org.springframework.security.core.userdetails.UserDetails;

import com.movie.models.User;

import java.util.List;
import java.util.Optional;


public interface UserService {
	User saveUser(User user);
	List<User> getAllUsers();
	Optional<User> getUserById(long id);
	User updateUser(User updatedUser);
	void deleteUser(long id);
	public UserDetails loadUserByUsername(String username);
	public void register(User user);
	public boolean checkIfUserExist(String username);

}
