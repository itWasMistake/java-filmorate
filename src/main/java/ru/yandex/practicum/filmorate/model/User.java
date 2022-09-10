package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
public class User {
    @NotNull
    private int id;
    @NotNull
    @Email
    @NotBlank
    private final String email;
    @NotNull
    @NotBlank
    private final String login;

    private String name;
    @Past
    @NotNull
    private final LocalDate birthday;
}
