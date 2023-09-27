package com.movie.controllers;

import com.movie.LoadDatabase;
import com.movie.controllers.assemblers.UserModelAssembler;
import com.movie.models.User;
import com.movie.repositories.UserRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
class UserControllerTest {
    private static final Logger log = LoggerFactory.getLogger(UserControllerTest.class);
    @Autowired
    private MockMvc mvc;
    @MockBean
    private UserController userController;

    @MockBean
    private UserModelAssembler assembler;

    private  UserRepository userRepository;
    @Before
    void init(){
        //creating test users
        log.info("Creating TEST USER {}",userRepository.save(new User("testUsername1","testPassword1")));
    }

    @Test
    void all() {
    }

    @Test
    void newUser() {
    }

    @Test
    void one() throws Exception {
    /*    given(userController.one(user.getUserId())).willReturn(assembler.toModel(user));

        mvc.perform(get("/users/"+user.getUserId())
                        .with(user("usernameTest").password("passwordTest"))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("user", is(user.getUsername())));*/
    }

    @Test
    void replaceUser() {
    }

    @Test
    void deleteUser() {
    }
}