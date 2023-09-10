package com.goCash.exception;

import org.springframework.http.HttpStatus;

public class ApiCallException extends RuntimeException{
    private final String message;

    private HttpStatus status;


    public ApiCallException(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
