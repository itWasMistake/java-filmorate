package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class User {
    @NotNull
    private int id;
    @NotNull
    @Email
    @NotBlank
    @Pattern(regexp = "^\\\\S*$")
    private final String email;
    @NotNull
    @NotBlank
    private final String login;

    private String name;
    @Past
    @NotNull
    private final LocalDate birthday;
}
