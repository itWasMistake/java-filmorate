package ru.yandex.practicum.filmorate.exceptions;

public class ValidationException extends RuntimeException {
    public ValidationException(final String msg) {
        super(msg);
    }
}
