package ru.yandex.practicum.filmorate.model;



public class ErrorResponse {
    final String error;

    public ErrorResponse(final String error) {
        this.error = error;
    }
    public String getError() {
      return   error;
    }
}
