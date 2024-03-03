package com.movie.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.config.TestConfig;
import com.movie.controllers.assemblers.UserModelAssembler;
import com.movie.models.User;
import com.movie.repositories.UserRepository;
import com.movie.services.impl.UserServiceImpl;
import org.apache.catalina.security.SecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



/**
 * Testing the interaction between the UserController and UserRepository layers.
 * It tests the controller's ability to handle HTTP requests and generate responses correctly,
 * and it also tests the repository's ability to interact with the database as expected when called by the controller.
 * **/
@Import(TestConfig.class)
@WithMockUser(username="admin",roles = "ADMIN")
@WebMvcTest(UserController.class)
class UserControllerTest {
    private static final Logger log = LoggerFactory.getLogger(UserControllerTest.class);
    @MockBean
    private UserRepository userRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserServiceImpl userService;
    @MockBean
    private UserModelAssembler assembler;

    //@BeforeEach for junit 5 (@before is junit 4)
    @BeforeEach
    void init(){
        //log.info("Adding test user {}",userRepository.save(new User("testUser","testPwd")));
    }

    @Test
    void shouldReturnAllUsers() throws Exception {


        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void shouldCreateUser() throws Exception  {
        User user = new User("newUsername","newPassword");
        user.setUserId(50L);
        String jsonUser=objectMapper.writeValueAsString(user);
        // Log the security context
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //log.info("Mock user: {}", authentication.isAuthenticated());
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
                 .content(jsonUser))
                .andExpect(status().isCreated())
                .andDo(print());

    }
/*
    @Test
    void shouldReturnUser() {


    }

    @Test
    void shouldUpdateUser() {
    }

    @Test
    void shouldDeleteUser() {
    }*/
}