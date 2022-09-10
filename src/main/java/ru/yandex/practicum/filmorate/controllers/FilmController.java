package ru.yandex.practicum.filmorate.controllers;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
@Data
public class FilmController {
    private int id = 1;

    private static final LocalDate DAY_OF_THE_FIRST_FILM = LocalDate.of(1895, 12, 28);
    private Map<Integer, Film> filmsMap = new HashMap<>();

    @PostMapping
    public Film createFilm(@RequestBody @Valid Film film) {
        log.info("Получен запрос к эндпоинту createFilm: " + film.toString());
        if (film.getReleaseDate().isBefore(DAY_OF_THE_FIRST_FILM)) {
            log.error("Была попытка создать фильм, с датой релиза раньше первого дня кино");
            throw new ValidationException("Фильм не может выйти в релиз раньше чем первый день кино");
        } else {
            film.setId(generateId());
            filmsMap.put(film.getId(), film);
            return film;
        }


    }

    @PutMapping
    public Film updateFilm(@RequestBody @Valid Film film) {
        log.info("Получен запрос к эндпоинту updateFilm: " + film.toString());
        if (filmsMap.containsKey(film.getId())) {
            if (film.getReleaseDate().isBefore(DAY_OF_THE_FIRST_FILM)) {
                throw new ValidationException("Фильм не может выйти в релиз раньше чем первый день кино");
            } else {
                filmsMap.replace(film.getId(), film);
                return film;
            }
        } else {
            log.info("Переданный ID фильма не существует. Будет создан новый фильм");
            if (film.getId() < 0) {
                throw new ValidationException("ID фильма не может быть отрицательным. Передано: " + film.getId());
            }
            if (film.getReleaseDate().isBefore(DAY_OF_THE_FIRST_FILM)) {
                throw new ValidationException("Фильм не может выйти в релиз раньше чем первый день кино");
            } else {
                film.setId(generateId());
                filmsMap.put(film.getId(), film);
                return film;
            }
        }
    }

    @GetMapping
    public Map<Integer, Film> findAll() {
        log.info("Получен запрос к эндпоинту findAll: " + filmsMap.toString());
        return filmsMap;
    }

    protected int generateId() {
        return ++id;
    }


}
