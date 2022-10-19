package ru.yandex.practicum.filmorate.inmemorystorage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private int id = 0;
    private final Map<Integer, User> usersMap = new HashMap<>();

    @Override
    public User createUser(User user) {
        user.setId(generateId());
        log.info("Получен запрос к эндпоинту createUser: " + user);
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
            usersMap.put(user.getId(), user);
        } else {
            usersMap.put(user.getId(), user);
        }

        return user;
    }

    public User getUserById(int userId) {
        log.info("Получен запрос к эндпоинту getUserById." +
                "Передано: " + userId);
        if (userId < 0) {
            throw new NotFoundException("Пользователь не может иметь отрицательный ID");
        } else if (!usersMap.containsKey(userId)) {
            throw new NotFoundException("Пользователь с таким id не существует");
        }
        return usersMap.get(userId);
    }

    @Override
    public User updateUser(User user) {
        log.info("Получен запрос к эндпоинту updateUser, передан ID: " + user);
        if (usersMap.containsKey(user.getId())) {
            usersMap.replace(user.getId(), user);
        } else {
            throw new NotFoundException("Пользователь не был найден...");
        }
        return user;
    }

    @Override
    public ArrayList<User> findAll() {
        log.info("Получен запрос к эндпоинту findAll: " + usersMap.values());
        return new ArrayList<>(usersMap.values());
    }

    public Map<Integer, User> getUsersMap() {
        return usersMap;
    }

    protected int generateId() {
        return ++id;
    }

}
