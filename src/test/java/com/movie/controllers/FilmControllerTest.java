package com.movie.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.models.Film;
import com.movie.services.impl.FilmServiceImpl;
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
 * Unit tests for FilmController response ; usage of a mock service layer with a predictable behavior
 **/
@SpringBootTest
@AutoConfigureMockMvc
class FilmControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private FilmServiceImpl filmService;

    @BeforeEach
    public void setup() throws Exception {
        // Initialize Mockito annotations
        MockitoAnnotations.openMocks(this);
        // Prepare mock data
        Film film1 = new Film("title1","genre1","director1");
        film1.setFilmId(1L);
        Film film2 = new Film("title2","genre2","director2");
        film2.setFilmId(2L);
        given(filmService.getAllFilms()).willReturn(Arrays.asList(film1, film2));
        // Mock getFilmById for specific film
        given(filmService.getFilmById(1)).willReturn(java.util.Optional.of(film1));
    }

    @Test
    @WithMockUser(username = "MockUser")
    public void getAllFilmsTest() throws Exception {
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
    public void newFilmTest() throws Exception {

        Film newFilm = new Film("newTitle","newGenre","newDirector");
        newFilm.setFilmId(10L); // Simulating auto-generated ID
        given(filmService.saveFilm(any(Film.class))).willReturn(newFilm);

        mvc.perform(MockMvcRequestBuilders
                        .post("/films")
                        .content(asJsonString(newFilm))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.filmId").value(10L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("newTitle"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value("newGenre"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.director").value("newDirector"));
    }


    @Test
    @WithMockUser(username = "MockUser")
    public void getFilmByIdTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/films/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.filmId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("title1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value("genre1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.director").value("director1"));
    }

    @Test
    @WithMockUser(username = "MockUser")
    public void replaceUserTest() throws Exception {
        Film modifiedFilm = new Film("modifiedTitle","modifiedGenre","modifiedDirector");
        modifiedFilm.setFilmId(1L);
        given(filmService.updateFilm(any(Film.class))).willReturn(modifiedFilm);

        mvc.perform(MockMvcRequestBuilders
                        .put("/films/{id}", 1L)
                        .content(asJsonString(modifiedFilm))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.filmId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("modifiedTitle"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value("modifiedGenre"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.director").value("modifiedDirector"));
    }

    @Test
    @WithMockUser(username = "MockUser")
    public void deleteFilmTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .delete("/films/{id}", 1L)
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