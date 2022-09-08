package ru.yandex.practicum.filmorate.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController("/films")
public class FilmController {

    private static final LocalDate DAY_OF_THE_FIRST_FILM = LocalDate.of(1895, 12, 28);
    Map<Integer, Film> filmsMap = new HashMap<>();

    @PostMapping(value = "/films/create-film")
    public Film createFilm(@RequestBody @Valid Film film) {
        log.info("Получен запрос к эндпоинту updateUser: /films/create-film");
        if (film.getReleaseDate().isBefore(DAY_OF_THE_FIRST_FILM)) {
            log.error("Была попытка создать фильм, с датой релиза раньше первого дня кино");
            throw new ValidationException("Фильм не может выйти в релиз раньше чем первый день кино");
        } else {
            filmsMap.put(film.getId(), film);
            return film;
        }


    }

    @PutMapping(value = "/films/update-film")
    public Film updateFilm(@RequestBody @Valid Film film, Integer id) {
        log.info("Получен запрос к эндпоинту updateUser: /films/update-film");
        if (film.getReleaseDate().isBefore(DAY_OF_THE_FIRST_FILM)) {
            throw new ValidationException("Фильм не может выйти в релиз раньше чем первый день кино");
        } else {
            filmsMap.replace(id, film);
            return film;
        }

    }

    @GetMapping(value = "/films")
    public Map<Integer, Film> findAll() {
        log.info("Получен запрос к эндпоинту updateUser: /films");
        return filmsMap;
    }

}
