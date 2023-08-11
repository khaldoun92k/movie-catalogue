package com.movie.services;

import org.springframework.security.core.userdetails.UserDetails;

import com.movie.models.User;


public interface UserService {
	public UserDetails loadUserByUsername(String username);
	public void register(User user);
	public boolean checkIfUserExist(String username);
}
