package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class Film {

    @NotNull
    private int id;
    @NotNull
    @NotBlank
    private final String name;
    @NotNull
    @Size(max=200)
    private final String description;
    @NotNull
    private final LocalDate releaseDate;
    @NotNull
    @Positive
    private final long duration;
}
