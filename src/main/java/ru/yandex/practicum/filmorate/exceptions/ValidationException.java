package ru.yandex.practicum.filmorate.exceptions;

public class ValidationException extends  Exception{
    public ValidationException(final String msg) {
        super(msg);
    }
}
