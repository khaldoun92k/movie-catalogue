package com.movie.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.models.Film;
import com.movie.services.UserService;
import com.movie.services.impl.FilmServiceImpl;
import com.movie.services.impl.RateServiceImpl;
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

import static org.hamcrest.Matchers.hasItems;
import static org.mockito.BDDMockito.given;
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

        given(filmService.getAllFilms()).willReturn(Arrays.asList(film1, film2));
        // Mock getFilmById for specific film
        given(filmService.getFilmById(1)).willReturn(Optional.of(film1));


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
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.rateList").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.rateList[*].rateId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.rateList[*].rating").value(2));

    }

    @Test
    @WithMockUser(username = "MockUser")
    public void getAllRatesTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/films")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.filmList").exists()) //Check if the filmList exists within the JSON response
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.filmList[*].filmId", hasItems(1, 2)));//Verify that the filmId are 1 and 2
        //filmList key is a result of the serialization of the CollectionModel containing the list of EntityModel<Film> objects.
    }

    @Test
    @WithMockUser(username = "MockUser")
    public void getRateByIdTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/films")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.filmList").exists()) //Check if the filmList exists within the JSON response
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.filmList[*].filmId", hasItems(1, 2)));//Verify that the filmId are 1 and 2
        //filmList key is a result of the serialization of the CollectionModel containing the list of EntityModel<Film> objects.
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
