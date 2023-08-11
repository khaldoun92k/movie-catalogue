package com.movie.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.movie.controllers.exceptions.UserAlreadyExistException;
import com.movie.models.User;
import com.movie.repositories.UserRepository;
import com.movie.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
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


	
	

}
