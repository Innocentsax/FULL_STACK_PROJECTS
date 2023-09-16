package com.decagon.chompapp.exceptions;

public class PasswordConfirmationException extends RuntimeException{

    private String message;

    public PasswordConfirmationException(String message) {

        super(String.format(message));
        this.message = message;

    }
}
