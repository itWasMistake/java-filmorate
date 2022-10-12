package ru.yandex.practicum.filmorate.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserController controller;
    private static User correctUser;
    private static User wrongUserEmail;
    private static User wrongUserDate;
    private static final LocalDate CORRECT_BIRTHDAY = LocalDate.of(1946, 8, 20);
    private static final LocalDate WRONG_BIRTHDAY = LocalDate.of(2446, 10, 20);

    @BeforeAll
    static void setUpUsers() {
        correctUser = new User("mail@mail.ru", "loginCorrectUser", CORRECT_BIRTHDAY);
        wrongUserDate = new User("email@mail.ru", "loginWrongUserDate", WRONG_BIRTHDAY);
        wrongUserEmail = new User("mail.ru", "loginWrongUserEmail", CORRECT_BIRTHDAY);
    }

    @AfterEach
    public void afterEach() {
        controller.getUsersMap().clear();
    }

    @Test
    void mustCreateCorrectUserAndReturnCode200ThenReturnUser() {
        try {
            mockMvc.perform(
                            post("/users")
                                    .content(objectMapper.writeValueAsString(correctUser))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(200))
                    .andExpect(jsonPath("$.id").isNumber())
                    .andExpect(jsonPath("$.login").value("loginCorrectUser"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void mustNotCreateWrongUserDateAndReturnCode400() {
        try {
            mockMvc.perform(
                            post("/users")
                                    .content(objectMapper.writeValueAsString(wrongUserDate))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void mustNotCreateWrongUserEmailAdnReturnCode400() {
        try {
            mockMvc.perform(
                            post("/users")
                                    .content(objectMapper.writeValueAsString(wrongUserEmail))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(400));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void updateUser() {
        User existingUser = new User("email@email.ru", "login", CORRECT_BIRTHDAY);
        existingUser.setId(4);
        controller.getUsersMap().put(existingUser.getId(), existingUser);
        correctUser.setId(4);
        try {
            mockMvc.perform(
                            put("/users")
                                    .content(objectMapper.writeValueAsString(correctUser))
                                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value("4"))
                    .andExpect(jsonPath("$.login").value("loginCorrectUser"));
            System.out.println(controller.getUsersMap());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}