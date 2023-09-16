package com.decagon.chompapp.exceptions;

public class InsufficientFundsException extends RuntimeException {
    private String message;

    public InsufficientFundsException(String message) {

        super(String.format(message));
        this.message = message;

    }
}
