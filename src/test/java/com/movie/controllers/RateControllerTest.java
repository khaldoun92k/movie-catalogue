package com.movie.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.models.Film;
import com.movie.models.Rate;
import com.movie.models.User;
import com.movie.models.keys.RateId;
import com.movie.services.UserService;
import com.movie.services.impl.FilmServiceImpl;
import com.movie.services.impl.RateServiceImpl;
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
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasItems;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit tests for RateController response ; usage of a mock service layer with a predictable behavior
 **/
@SpringBootTest
@AutoConfigureMockMvc
public class RateControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private FilmServiceImpl filmService;
    @MockBean
    private UserServiceImpl userService;
    @MockBean
    private RateServiceImpl rateService;

    @BeforeEach
    public void setup() throws Exception {
        // Initialize Mockito annotations
        MockitoAnnotations.openMocks(this);
        // Prepare mock data
        Film film1 = new Film("title1", "genre1", "director1");
        film1.setFilmId(1L);
        Film film2 = new Film("title2", "genre2", "director2");
        film2.setFilmId(2L);

        User mockUser = new User("MockUser", "password");
        mockUser.setUserId(1L);

        Rate mockRate1 = new Rate();
        mockRate1.setRateId(new RateId(mockUser.getUserId(), film1.getFilmId()));
        mockRate1.setUser(mockUser);
        mockRate1.setFilm(film1);
        mockRate1.setRating(1L);
        Rate mockRate2 = new Rate();
        mockRate2.setRateId(new RateId(mockUser.getUserId(), film2.getFilmId()));
        mockRate2.setUser(mockUser);
        mockRate2.setFilm(film2);
        mockRate2.setRating(5L);


        // Mock film
        given(filmService.getFilmById(1L)).willReturn(Optional.of(film1));
        given(filmService.getFilmById(2L)).willReturn(Optional.of(film2));
        // Mock current user
        given(userService.loadUserByUsername("MockUser")).willReturn(mockUser);

        given(rateService.getRateById(new RateId(mockUser.getUserId(), film1.getFilmId()))).willReturn(Optional.of(mockRate1));
        given(rateService.getRateById(new RateId(mockUser.getUserId(), film2.getFilmId()))).willReturn(Optional.of(mockRate2));

        given(rateService.getAllRates()).willReturn(List.of(mockRate1, mockRate2));

    }

    @Test
    @WithMockUser(username = "MockUser")
    public void rateFilmTest() throws Exception {
        RateController.RatingRequest ratingRequest = new RateController.RatingRequest(1L, 2L);
        mvc.perform(MockMvcRequestBuilders
                        .post("/rating")
                        .content(asJsonString(ratingRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Film 1 is rated 2 successfully by user MockUser"));

    }

    @Test
    @WithMockUser(username = "MockUser")
    public void getAllRatesTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/rates")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.rateList").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.rateList[0].rateId.user").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.rateList[0].rateId.film").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.rateList[0].rating").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.rateList[1].rateId.user").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.rateList[1].rateId.film").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.rateList[1].rating").value(5));
    }

    @Test
    @WithMockUser(username = "MockUser")
    public void getRateByIdTest() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                        .get("/rates/{userId}/{filmId}", 1L, 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.rateId.user").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rateId.film").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rating").value(1));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
