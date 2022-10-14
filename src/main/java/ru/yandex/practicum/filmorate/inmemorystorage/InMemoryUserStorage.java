package ru.yandex.practicum.filmorate.inmemorystorage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.HashMap;
import java.util.Map;


@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private int id = 1;
    private final Map<Integer, User> usersMap = new HashMap<>();

    @Override
    public User createUser(User user) {
        user.setId(generateId());
        log.info("Получен запрос к эндпоинту createUser: " + user);
        if (user.getName() == null) {
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
            throw new NotFoundException("Пользовательн не может иметь отрицательный ID");
        }
        return usersMap.get(userId);
    }

    @Override
    public User updateUser(User user) {
        log.info("Получен запрос к эндпоинту updateUser, передан ID: " + user);
        if (usersMap.containsKey(user.getId())) {
            usersMap.replace(user.getId(), user);
        } else {
            log.info("Переданный ID пользователя не существует, будет создан новый");
            user.setId(generateId());
            createUser(user);


        }
        return user;
    }

    @Override
    public Map<Integer, User> findAll() {
        log.info("Получен запрос к эндпоинту findAll: " + usersMap.values());
        return usersMap;
    }

    protected int generateId() {
        return ++id;
    }

}
