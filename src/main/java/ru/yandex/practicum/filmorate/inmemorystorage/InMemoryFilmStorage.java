package ru.yandex.practicum.filmorate.inmemorystorage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    private int id = 1;

    private static final LocalDate DAY_OF_THE_FIRST_FILM = LocalDate.of(1895, 12, 28);
    private final Map<Integer, Film> filmsMap = new HashMap<>();

    @Override
    public Film createFilm(Film film) {
        log.info("Получен запрос к эндпоинту createFilm: " + film.toString());
        if (film.getReleaseDate().isBefore(DAY_OF_THE_FIRST_FILM)) {
            throw new ValidationException("Фильм не мог выйти в релиз раньше выхода первого в мире кино");
        }
        film.setId(generateId());
        filmsMap.put(film.getId(), film);
        return film;
    }

    public Film getFilmById(int filmId) {
        if (filmId < 0) {
            throw new RuntimeException("Id фильма не может быть отрицательным числом");
        }
        return filmsMap.get(filmId);
    }

    @Override
    public Film updateFilm(Film film) {
        log.info("Получен запрос к эндпоинту updateFilm: " + film.toString());
        if (filmsMap.containsKey(film.getId())) {
            filmsMap.replace(film.getId(), film);
        } else {
            log.info("Переданный ID фильма не существует. Будет создан новый фильм");
            film.setId(generateId());
            createFilm(film);
        }
        return film;
    }

    @Override
    public Map<Integer, Film> findAll() {
        log.info("Получен запрос к эндпоинту findAll: " + filmsMap);
        return filmsMap;
    }


    protected int generateId() {
        return ++id;
    }
}
