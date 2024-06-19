package com.movie.services.impl;

import com.movie.controllers.exceptions.UserAlreadyExistException;
import com.movie.models.User;
import com.movie.repositories.UserRepository;
import com.movie.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User saveUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public Optional<User> getUserById(long id) {
		return userRepository.findById(id);
	}

	@Override
	public User updateUser(User updatedUser) {
		return userRepository.save(updatedUser);
	}

	@Override
	public void deleteUser(long id) {
		userRepository.deleteById(id);
	}

	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByUsername(username);
        user.orElseThrow(() -> new UsernameNotFoundException("User Not Found: " + username));
        return user.get();
	}

	@Override
	public void register(User user) throws UserAlreadyExistException{
		 // check if user already registered with us
        if(checkIfUserExist(user.getUsername())){
            throw new UserAlreadyExistException("User already exists for this username");
        }
        userRepository.save(user);
	}
	 @Override
	    public boolean checkIfUserExist(String username) {
		 	Optional<User> user=userRepository.findByUsername(username);
	        return  !user.isEmpty();
	    }

	@Override
	public void deleteAllUsers() {
		userRepository.deleteAll();
	}


}
