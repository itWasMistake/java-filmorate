package ru.yandex.practicum.filmorate.controllers;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController("/users")
@Data
public class UserController {
    private Map<Integer, User> usersMap = new HashMap<>();

    @PostMapping(value = "/users/create-user")
    public User createUser(@RequestBody @Valid User user) {
        if (user.getName() == null) {
            log.info("Получен запрос к эндпоинту createUser: /users/create-user, поле name оказалось null");
            user.setName(user.getLogin());
            usersMap.put(user.getId(), user);
        }
        log.info("Получен запрос к эндпоинту createUser: /users/create-user");
        usersMap.put(user.getId(), user);
        return user;
    }

    @PutMapping("/users/update-user")
    public User updateUser(@RequestBody @Valid User user, Integer id) {
        log.info("Получен запрос к эндпоинту updateUser: /users/update-user");
        usersMap.replace(id, user);
        return user;
    }

    @GetMapping(value = "/users")
    public Map<Integer, User> findAll() {
        log.info("Получен запрос к эндпоинту findAll: /users");
        return usersMap;
    }

}
