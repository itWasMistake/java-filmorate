package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Data
@Valid
public class Film {

    private  Set<Integer> userLikes = new HashSet<>();
    @NotNull
    private final int rate;
    @NotNull
    private int likesCount;
    @NotNull
    private int id;
    @NotNull
    @NotBlank
    private final String name;
    @NotNull
    @Size(max = 200)
    private final String description;
    @NotNull
    private final LocalDate releaseDate;
    @NotNull
    @Positive
    private final long duration;

    public void increase() {
        ++likesCount;
    }

    public void reduce() {
        --likesCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return likesCount == film.likesCount && id == film.id && duration == film.duration && Objects.equals(userLikes, film.userLikes) && Objects.equals(name, film.name) && Objects.equals(description, film.description) && Objects.equals(releaseDate, film.releaseDate);
    }

    @Override
    public String toString() {
        return "Film{" +
                "userLikes=" + userLikes +
                ", likesCount=" + likesCount +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", releaseDate=" + releaseDate +
                ", duration=" + duration +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(userLikes, likesCount, id, name, description, releaseDate, duration);
    }
}
