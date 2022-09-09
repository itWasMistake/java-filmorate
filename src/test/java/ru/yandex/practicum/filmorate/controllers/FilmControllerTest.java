package ru.yandex.practicum.filmorate.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class FilmControllerTest {

    private static final LocalDate CORRECT_DATE_DAY_OF_FILMS = LocalDate.of(1895, 12, 28);
    private static final LocalDate RELEASE_OF_THE_HOUSE_THAT_JACK_BUILT = LocalDate.of(2018, 5, 14);
    private static final LocalDate WRONG_RELEASE_DATE = LocalDate.of(1894, 11, 27);

    private static Film correctFilm;
    private static Film theHouseThatJackBuilt;
    private static Film wrongFilm;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private FilmController controller;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    public static void setUpFilms() {
        correctFilm = new Film(1, "Correct-Film",
                RandomString.make(200), CORRECT_DATE_DAY_OF_FILMS, 90);

        theHouseThatJackBuilt = new Film(2, "The House That Jack Built",
                "The story follows Jack, a highly intelligent serial killer, over the course of twelve years, " +
                        "and depicts the murders that really develop his inner madman.",
                RELEASE_OF_THE_HOUSE_THAT_JACK_BUILT, 152L);

        wrongFilm = new Film(3, "WrongFilm", "WrongFilmDescription",
                WRONG_RELEASE_DATE, -100);

    }

    @AfterEach
    public void afterEach() {
        controller.getFilmsMap().clear();
    }

    @Test
    void mustCreateCorrectFilmAndReturnCode200ThenReturnFilm() {
        try {
            mockMvc.perform(
                            post("/films/create-film")
                                    .content(objectMapper.writeValueAsString(correctFilm))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(200))
                    .andExpect(jsonPath("$.id").isNumber())
                    .andExpect(jsonPath("$.name").value("Correct-Film"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void mustNotCreateWrongFilmAndReturnCode400() {
        try {
            mockMvc.perform(
                            post("/films/create-film")
                                    .content(objectMapper.writeValueAsString(wrongFilm))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void mustCreateExistingFilmAndReturnCode200ThenReturnFilm() {
        try {
            mockMvc.perform(
                            post("/films/create-film")
                                    .content(objectMapper.writeValueAsString(theHouseThatJackBuilt))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(200))
                    .andExpect(jsonPath("$.id").isNumber())
                    .andExpect(jsonPath("$.name").value("The House That Jack Built"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void updateFilm() {
        Film film = new Film(121, "Film", "description", LocalDate.now(), 90);
        controller.getFilmsMap().put(film.getId(), film);
        try {
        mockMvc.perform(
                put("/films/update-film/{id}", 121)
                        .content(objectMapper.writeValueAsString(correctFilm))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Correct-Film"));
        System.out.println(controller.getFilmsMap());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void findAll() {
        controller.getFilmsMap().put(correctFilm.getId(), correctFilm);
        controller.getFilmsMap().put(theHouseThatJackBuilt.getId(), theHouseThatJackBuilt);
        try {
            mockMvc.perform(
                            get("/films")
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(controller.getFilmsMap())));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}