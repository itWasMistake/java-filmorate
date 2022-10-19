package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.inmemorystorage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {
    private final InMemoryUserStorage userStorage;

    @Autowired
    public UserService(final InMemoryUserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User addFriend(int friendId, int myId) {
        log.info("Получен запрос к эндпоинту addFriend." +
                "Передано: " + "Id друга: " + friendId + " Мой ID: " + myId);
        User wantsToBeFriends = userStorage.getUserById(myId);
        User whoIsBeingAdded = userStorage.getUserById(friendId);
        whoIsBeingAdded.getFiendsSet().add(myId);
        wantsToBeFriends.getFiendsSet().add(friendId);
        log.info("Добавлены в друзья");
        return whoIsBeingAdded;
    }

    public User delFriend(int friendId, int myId) {
        log.info("Получен запрос к эндпоинту delFriend." +
                "Передано: " + friendId + myId);
        User whoDeleted = userStorage.findAll().get(friendId);
        User whoDeleting = userStorage.findAll().get(myId);
        whoDeleted.getFiendsSet().remove(whoDeleting.getId());
        return whoDeleted;
    }

    public List<User> getAllFriends(int myId) {
        log.info("Получен запрос к эндпоинту getAllFriends." +
                "Передано: " + myId);
        return userStorage.getUserById(myId)
                .getFiendsSet()
                .stream()
                .map(userStorage.getUsersMap()::get)
                .collect(Collectors.toList());

    }


    public List<User> getCommonFriends(int findUserId, int myId) {
        log.info("Получен запрос к эндпоинту getCommonFriends." +
                "Передано: " + findUserId + " " + myId);
        return userStorage.getUserById(findUserId).getFiendsSet().stream()
                .filter(id -> userStorage
                        .getUserById(myId)
                        .getFiendsSet()
                        .contains(id))
                .map(userStorage.getUsersMap()::get)
                .collect(Collectors.toList())
                ;

    }

}
