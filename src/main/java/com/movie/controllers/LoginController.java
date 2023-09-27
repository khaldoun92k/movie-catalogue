package com.movie.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.movie.models.User;
import com.movie.services.impl.UserServiceImpl;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
public class LoginController {

	final static Logger logger = LogManager.getLogger(LoginController.class);
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	@Autowired
	private UserServiceImpl userDetailsService;
	
	//record introduced in JDK 14
	record LoginRequest (String username, String password){}
	@CrossOrigin
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Store authentication object in session
	    ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
	    HttpSession session = attr.getRequest().getSession(true); 
	    session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
	    
	    //logging current user
	    if (!(authentication instanceof AnonymousAuthenticationToken)) {
	        String currentUserName = authentication.getName();
	        logger.info("currentUserName  {}" , currentUserName);
	    }
	    
	    
		// Normally return a JWT or some other token here but here it is using server based session
		return ResponseEntity.ok("Authenticated successfully");

		/*
	    UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(
	            loginRequest.username(), loginRequest.password()); 
	        Authentication authentication = authenticationManager.authenticate(token); 
	        SecurityContext context = securityContextHolderStrategy.createEmptyContext();
	        context.setAuthentication(authentication); 
	        securityContextHolderStrategy.setContext(context);
	        securityContextRepository.saveContext(context, request, response); 
		*/
		
	}
	
	@CrossOrigin
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid  @RequestBody User user) {	//@Valid to trigger the validation 
		userDetailsService.register(user);
		return ResponseEntity.ok("New user added successfully");	
	}
}
