package com.example.decapay.exceptions;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
@AllArgsConstructor
public class UserNotFoundException extends RuntimeException {
    private HttpStatus status;
    private String message;

    public UserNotFoundException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }
}