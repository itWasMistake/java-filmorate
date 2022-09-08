package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Data
public class Film {
    @NotNull
    private final int id;
    @NotNull
    @NotBlank
    private final String name;
    @NotNull
    private final String description;
    @NotNull
    private final LocalDate releaseDate;
    @NotNull
    @Positive
    private final long duration;

}
