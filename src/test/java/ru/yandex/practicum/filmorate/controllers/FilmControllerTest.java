package ru.yandex.practicum.filmorate.controllers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureMockMvc
class FilmControllerTest {

    @BeforeAll
    static void beforeAll() {
        Film correctFilm = new Film(1, "CorrectFilm", "CorrectFilmDescription",
                LocalDate.now(), 100L);
        Film
    }


    @Test
    void createFilm() {
    }

    @Test
    void updateFilm() {
    }

    @Test
    void findAll() {
    }
}