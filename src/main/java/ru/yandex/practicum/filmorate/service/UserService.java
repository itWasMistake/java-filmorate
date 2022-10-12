package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.inmemorystorage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.security.PublicKey;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {
    private final InMemoryUserStorage userStorage;

    @Autowired
    public UserService (final InMemoryUserStorage userStorage) {
        this.userStorage = userStorage;
    }
    public User addFriend(int friendId, int myId) {
        if (friendId < 0 || myId < 0) {
            throw new ValidationException("ID друга или мой id не может быть отрицательным числом");
        }
       log.info("Получен запрос к эндпоинту addFriend." +
               "Передано: " + "Id друга: " + friendId + "Мой ID:" + myId);
        User whoAdded = userStorage.findAll().get(friendId);
        User whoAdds = userStorage.findAll().get(myId);
        whoAdds.getFiendsSet().add(friendId);
        whoAdded.getFiendsSet().add(myId);
        log.info("Добавлены в друзья");
        return whoAdded;
    }
    public User delFriend(int friendId, int myId) {
        log.info("Получен запрос к эндпоинту delFriend." +
                "Передано: " + friendId + myId);
        User whoDeleted = userStorage.findAll().get(friendId);
        User whoDeleting = userStorage.findAll().get(myId);
        whoDeleted.getFiendsSet().remove(whoDeleting.getId());
        whoDeleting.getFiendsSet().remove(whoDeleted.getId());
        return whoDeleted;
    }

    public List<User> getAllFriends(int myId) {
        log.info("Получен запрос к эндпоинту getAllFriends." +
                "Передано: "  + myId);
        return userStorage.findAll().keySet().stream()
                .filter((n) -> n == myId)
                .map(userStorage.findAll()::get)
                .collect(Collectors.toList());

    }


    public List<User> getCommonFriends(int findUserId, int myId) {
        log.info("Получен запрос к эндпоинту delFriend." +
                "Передано: " + findUserId + myId);
        return userStorage.getUserById(findUserId)
                .getFiendsSet()
                .stream()
                .filter((n) -> n == myId)
                .map(userStorage.findAll()::get)
                .collect(Collectors.toList());
    }

}
