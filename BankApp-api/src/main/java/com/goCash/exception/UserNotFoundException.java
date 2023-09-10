package com.goCash.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends RuntimeException {
    private String message;
    private HttpStatus httpStatus;

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public UserNotFoundException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
