package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.Map;

@RestController("/users")
public class UserController {
    Map<Integer, User> usersMap = new HashMap<>();

    @PostMapping(value = "/users/create-user")
    public User createUser(@RequestBody User user) {
        usersMap.put(user.getId(), user);
        return user;
    }

    @PutMapping("/users/update-user")
    public User updateUser(@RequestBody User user, int id) {
        usersMap.replace(id, user);
        return user;
    }
    @GetMapping
    public Map<Integer, User> findAll() {
        return usersMap;
    }

}
