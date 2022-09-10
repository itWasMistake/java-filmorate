package ru.yandex.practicum.filmorate.controllers;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
@Data
public class UserController {
    private int id = 1;
    private Map<Integer, User> usersMap = new HashMap<>();

    @PostMapping
    public User createUser(@RequestBody @Valid User user) {
        log.info("Получен запрос к эндпоинту createUser: " + user.toString());
        if (user.getName() == null) {

            user.setName(user.getLogin());
            usersMap.put(user.getId(), user);
        }
        user.setId(generateId());

        usersMap.put(user.getId(), user);
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody @Valid User user) {
        log.info("Получен запрос к эндпоинту updateUser, передан ID: " + user.toString());
        if (usersMap.containsKey(user.getId())) {
            usersMap.replace(user.getId(), user);
            return user;
        } else {
            log.info("Переданный ID пользователя не существует, будет создан новый");
            if (user.getId() < 0) {
                log.info("ID пользователя не может быть отрицательным. Передано: " + user.getId());
                throw new ValidationException("Нельзя создать пользователя с отрицательным ID");
            } else {
                usersMap.put(user.getId(), user);
                return user;
            }

        }


    }

    @GetMapping
    public Map<Integer, User> findAll() {
        log.info("Получен запрос к эндпоинту findAll: " + usersMap.toString());
        return usersMap;
    }

    protected int generateId() {
        return ++id;
    }
}
