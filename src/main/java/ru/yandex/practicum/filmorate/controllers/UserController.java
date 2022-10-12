package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.inmemorystorage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class UserController {


    private final InMemoryUserStorage userStorage;
    private final UserService userService;


    @Autowired
    public UserController(UserService userService, InMemoryUserStorage userStorage) {
        this.userService = userService;
        this.userStorage = userStorage;
    }

    @RequestMapping(value = "/users/{id}/friends/{friendId}",
            method = RequestMethod.PUT
    )
    public User addFriend(@PathVariable(value = "id") String id, @PathVariable(value = "friendId") String friendId) {
        return userService.addFriend(Integer.parseInt(friendId), Integer.parseInt(id));
    }

    @RequestMapping(value = "/users/{id}/friends/{friendId}",
            method = RequestMethod.DELETE)
    public User delFriend(@PathVariable(value = "id") @Valid String id, @PathVariable(value = "friendId") String friendId) {
        return userService.delFriend(Integer.parseInt(id), Integer.parseInt(id));
    }

    @RequestMapping(value = "/users/{id}/friends",
            method = RequestMethod.GET)
    public List<User> getFriends(@PathVariable(value = "id") String id) {
        return userService.getAllFriends(Integer.parseInt(id));
    }

    @RequestMapping(value = "/users/{id}/friends/common/{otherId}",
            method = RequestMethod.GET)
    public List<User> getCommonFriends(@PathVariable(value = "id") int id, @PathVariable(value = "otherId") int otherId) {
        return userService.getCommonFriends(otherId, id);
    }

    @PostMapping("/users")
    public User createUser(@RequestBody @Valid User user) {

        return userStorage.createUser(user);
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody @Valid User user) {
        return userStorage.updateUser(user);
    }

    @GetMapping("/users")
    public Map<Integer, User> findAll() {
        return userStorage.findAll();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable String id) {
        return userStorage.getUserById(Integer.parseInt(id));
    }

}
