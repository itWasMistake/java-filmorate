package ru.yandex.practicum.filmorate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.inmemorystorage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
public class FilmController {
    private final FilmService filmService;
    private final InMemoryFilmStorage filmStorage;

    @Autowired
    public FilmController(FilmService filmService, InMemoryFilmStorage filmStorage) {
        this.filmService = filmService;
        this.filmStorage = filmStorage;

    }


    @RequestMapping(value = "/films/{id}/like/{userId}",
            method = RequestMethod.PUT)
    public Film putLike(@PathVariable(value = "id") String id, @PathVariable(value = "userId") String userId) {
        return filmService.addLike(Integer.parseInt(id), Integer.parseInt(userId));
    }

    @RequestMapping(value = "/films/{id}/like/{userId}",
            method = RequestMethod.DELETE)
    public Film delLike(@PathVariable(value = "id") String filmId, @PathVariable(value = "userId") String userId) {
        return filmService.removeLike(Integer.parseInt(filmId), Integer.parseInt(userId));
    }

    @RequestMapping(value = "/films/popular",
            method = RequestMethod.GET)
    public List<Film> getPopularFilms(@RequestParam(value = "count", required = false, defaultValue = "10") String count) {
            filmService.mostPopularMovies(10);
        return filmService.mostPopularMovies(Integer.parseInt(count));
    }

    @PostMapping("/films")
    public Film createFilm(@RequestBody @Valid Film film) {
        return filmStorage.createFilm(film);
    }

    @PutMapping("/films")
    public Film updateFilm(@RequestBody @Valid Film film) {
        return filmStorage.updateFilm(film);
    }

    @GetMapping("/films")
    public Map<Integer, Film> findAll() {
        return filmStorage.findAll();
    }
}
