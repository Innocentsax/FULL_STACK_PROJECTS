package com.goCash.exception;

import org.springframework.http.HttpStatus;

public class WalletNotFoundException extends RuntimeException{
    private String message;
    private HttpStatus httpStatus;

    public WalletNotFoundException(String message) {
        super(message);
    }

    public WalletNotFoundException(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public WalletNotFoundException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
