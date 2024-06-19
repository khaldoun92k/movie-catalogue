package com.movie.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.models.User;
import com.movie.services.impl.UserServiceImpl;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Unit tests for UserController response
 **/
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private UserServiceImpl userService;

    @BeforeEach
    public void setup() throws Exception{
        // Initialize Mockito annotations
        MockitoAnnotations.openMocks(this);

        // Mock UserService saveUser method to actually save user
        given(userService.saveUser(any(User.class)))
                .willAnswer(invocation -> invocation.getArgument(0)); // Return the saved user as-is

        // Prepare mock data for getAllUsers
        User user1 = new User("user1", "password1");
        user1.setUserId(1L);
        User user2 = new User("user2", "password2");
        user2.setUserId(2L);
        given(userService.getAllUsers()).willReturn(Arrays.asList(user1, user2));

        // Mock getUserById for specific user
        given(userService.getUserById(1L)).willReturn(java.util.Optional.of(user1));
    }

    @Test
    @WithMockUser(username = "MockUser", roles = {"USER"})
    public void getAllUsersTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.userList").exists()) //Check if the userList exists within the JSON response
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.userList[*].userId").isNotEmpty());//Verify that the userId exists for every user in the userList.
        //userList key is a result of the serialization of the CollectionModel containing the list of EntityModel<User> objects.
    }

    @Test
    @WithMockUser(username = "MockUser", roles = {"USER"})
    public void newUserTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .post("/users")
                        .content(asJsonString(new User("userTest", "pwdTest")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").exists());
    }


    @Test
    @WithMockUser(username = "MockUser", roles = {"USER"})
    public void getUserByIdTest()throws Exception {
        Long userId = 1L;
        User user = new User("userTest", "passwordTest");
        user.setUserId(userId);
        given(userService.getUserById(userId)).willReturn(Optional.of(user));

        mvc.perform(MockMvcRequestBuilders
                        .get("/users/{id}", userId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(userId));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}