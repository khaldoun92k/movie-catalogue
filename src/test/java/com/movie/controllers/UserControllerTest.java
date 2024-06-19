package com.movie.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.models.User;
import com.movie.services.impl.UserServiceImpl;
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

import static org.hamcrest.Matchers.hasItems;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Unit tests for UserController response ; usage of a mock service layer with a predictable behavior
 **/
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private UserServiceImpl userService;

    @BeforeEach
    public void setup() throws Exception {
        // Initialize Mockito annotations
        MockitoAnnotations.openMocks(this);
        // Prepare mock data
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
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.userList[*].userId", hasItems(1, 2)));//Verify that the userId are 1 and 2
        //userList key is a result of the serialization of the CollectionModel containing the list of EntityModel<User> objects.
    }

    @Test
    @WithMockUser(username = "MockUser", roles = {"USER"})
    public void newUserTest() throws Exception {
        User newUser = new User("newUser", "newPassword");
        newUser.setUserId(10L); // Simulating auto-generated ID
        given(userService.saveUser(any(User.class))).willReturn(newUser);

        mvc.perform(MockMvcRequestBuilders
                        .post("/users")
                        .content(asJsonString(newUser))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(10L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("newUser"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("newPassword"));
    }


    @Test
    @WithMockUser(username = "MockUser", roles = {"USER"})
    public void getUserByIdTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/users/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("user1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("password1"));
    }

    @Test
    @WithMockUser(username = "MockUser", roles = {"USER"})
    public void replaceUserTest() throws Exception {
        User modifiedUser = new User("modifiedUsername", "modifiedPassword");
        modifiedUser.setUserId(1L);
        given(userService.updateUser(any(User.class))).willReturn(modifiedUser);

        mvc.perform(MockMvcRequestBuilders
                        .put("/users/{id}", 1L)
                        .content(asJsonString(modifiedUser))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("modifiedUsername"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("modifiedPassword"));
    }

    @Test
    @WithMockUser(username = "MockUser", roles = {"USER"})
    public void deleteUserTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .delete("/users/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}