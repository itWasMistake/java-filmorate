package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Valid
public class User {
    Set<Integer> fiendsSet = new HashSet<>();
    private int id;
    @Email
    @NotBlank
    @NotNull
    private final String email;
    @NotNull
    @NotBlank
    private final String login;

    private String name;
    @Past
    @NotNull
    private final LocalDate birthday;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(fiendsSet, user.fiendsSet) && Objects.equals(email, user.email) && Objects.equals(login, user.login) && Objects.equals(name, user.name) && Objects.equals(birthday, user.birthday);
    }

    @Override
    public String toString() {
        return "User{" +
                "fiendsSet=" + fiendsSet +
                ", id=" + id +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(fiendsSet, id, email, login, name, birthday);
    }
}
