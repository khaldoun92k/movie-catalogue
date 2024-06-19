package com.movie.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.movie.web.CustomAuthenticationProvider;

@Configuration
@EnableWebSecurity // Beginning with version 5.7.0-M2, Spring deprecates the use of WebSecurityConfigureAdapter and suggests creating configurations without it.
public class SecurityConfiguration {
	
    @Autowired
    private CustomAuthenticationProvider authProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // TODO CSRFs (Cross site request forgery) will be enabled later
                .authorizeHttpRequests( auth -> auth
                		.requestMatchers("/", "/index.html", "*.ico", "*.css", "*.js", "/login","/home","/register").permitAll() //open rest paths
                        .anyRequest().authenticated() // The user should be authenticated for any request in the application.
                )
                .authenticationProvider(authProvider) 
                //SessionCreationPolicy.IF_REQUIRED is the default if not set
                //.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Spring Security will never create an HttpSession and it will never use it to obtain the Security Context (JWT case).
                //.httpBasic(Customizer.withDefaults()) // Spring Security’s HTTP Basic Authentication support is enabled by default. However, as soon as any servlet-based configuration is provided, HTTP Basic must be explicitly provided.
                .build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = 
            http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(authProvider);
        return authenticationManagerBuilder.build();
    }
  
     

    
    
  
}