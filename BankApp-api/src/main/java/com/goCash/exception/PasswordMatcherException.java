package com.goCash.exception;

import org.springframework.http.HttpStatus;

public class PasswordMatcherException extends RuntimeException {
    private String message;
    private HttpStatus httpStatus;

    public PasswordMatcherException(String message) {
        this.message = message;
    }

    public PasswordMatcherException(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public PasswordMatcherException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
