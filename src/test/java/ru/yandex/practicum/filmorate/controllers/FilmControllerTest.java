package ru.yandex.practicum.filmorate.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        correctFilm = new Film("CorrectFilmName", "CorrectFilm", CORRECT_DATE_DAY_OF_FILMS, 90);

        theHouseThatJackBuilt = new Film("The House That Jack Built",
                "The story follows Jack, a highly intelligent serial killer",
                RELEASE_OF_THE_HOUSE_THAT_JACK_BUILT, 152L);

        wrongFilm = new Film("WrongFilm", "WrongFilm",
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
                            post("/films")
                                    .content(objectMapper.writeValueAsString(correctFilm))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(200))
                    .andExpect(jsonPath("$.id").isNumber())
                    .andExpect(jsonPath("$.name").value("CorrectFilmName"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void mustNotCreateWrongFilmAndReturnCode400() {
        try {
            mockMvc.perform(
                            post("/films")
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
                            post("/films")
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
        Film film = new Film("name", "Film", LocalDate.now(), 90);
        film.setId(4);
        controller.getFilmsMap().put(film.getId(), film);
        correctFilm.setId(4);
        System.out.println(controller.getFilmsMap());
        try {
            mockMvc.perform(
                            put("/films")
                                    .content(objectMapper.writeValueAsString(correctFilm))
                                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value("4"))
                    .andExpect(jsonPath("$.name").value("CorrectFilmName"));
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