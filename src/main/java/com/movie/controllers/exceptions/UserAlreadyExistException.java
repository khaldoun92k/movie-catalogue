package com.movie.controllers.exceptions;

public class UserAlreadyExistException extends RuntimeException{
	
	public UserAlreadyExistException(String id) {
		super("Username already taken "+id);
	}
}
