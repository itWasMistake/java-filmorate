package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;

public interface UserStorage {
    User createUser(User user);


    User updateUser(User user);

   ArrayList<User> findAll();
}
