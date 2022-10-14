package ru.yandex.practicum.filmorate.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.inmemorystorage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService {
    private final InMemoryFilmStorage filmStorage;

    @Autowired
    public FilmService(final InMemoryFilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Film addLike(int filmId, int userId) {
        filmStorage.getFilmById(filmId).increase();
        filmStorage.getFilmById(filmId).getUserLikes().add(userId);

        return filmStorage.getFilmById(filmId);
    }


    public Film removeLike(int filmId, int userId) {
        if (filmStorage.findAll().get(filmId).getUserLikes().remove(userId)) {
            filmStorage.findAll().get(filmId).reduce();
            return filmStorage.findAll().get(filmId);
        }
        return null;
    }

    public List<Film> mostPopularMovies(int count) {
        log.info("Получен запрос на получение популярных фильмов");
        return filmStorage.findAll().values().stream()
                .sorted(Comparator.comparing(Film::getLikesCount).reversed())
                .limit(count)
                .collect(Collectors.toList());
    }
}
