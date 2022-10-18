package ru.yandex.practicum.filmorate.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.inmemorystorage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
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
        Film currFilm = filmStorage.getFilmById(filmId);
        currFilm.increase();
        currFilm.getUserLikes().add(userId);

        return currFilm;
    }


    public Film removeLike(int filmId, int userId) {
        Film currFilm = filmStorage.getFilmById(filmId);
        if (Objects.nonNull(currFilm) && currFilm.getUserLikes().contains(userId)) {
            currFilm.getUserLikes().remove(userId);
            currFilm.reduce();
            return currFilm;
        } else if (Objects.isNull(currFilm)) {
            throw new NotFoundException("Фильм с таким id не был найден");
        } else if (!currFilm.getUserLikes().contains(userId)) {
            throw new NotFoundException("Пользователь с таким id не ставил лайк фильму.");
        }
        return currFilm;
    }

    public List<Film> mostPopularMovies(int count) {
        log.info("Получен запрос на получение популярных фильмов");
        return filmStorage.findAll().stream()
                .sorted(Comparator.comparing(Film::getLikesCount).reversed())
                .limit(count)
                .collect(Collectors.toList());
    }
}
