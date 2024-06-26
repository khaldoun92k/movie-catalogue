package com.movie.web;

import com.movie.services.impl.UserServiceImpl;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	@Resource
    UserServiceImpl userDetailsService;

    @Override
	public  Authentication authenticate(Authentication authentication)  
            throws AuthenticationException {
    	String username = authentication.getName();
        String password = authentication.getCredentials().toString();
    	
        UserDetails user=userDetailsService.loadUserByUsername(username);
        if (user == null) {
            throw new BadCredentialsException("Authentication failed");
        }

        if (!password.equals(user.getPassword())) {
            throw new BadCredentialsException("Authentication failed");
        }
        return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());//use UserDetails as a principal
        //If authentication succeeded, you need to return a fully initialized UsernamePasswordAuthenticationToken.
        
    }
    
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
