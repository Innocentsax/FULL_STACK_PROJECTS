package com.decagon.chompapp.exceptions;

public class WalletCannotBeAccessedException extends RuntimeException{
    private String message;

    public WalletCannotBeAccessedException(String message) {

        super(String.format(message));
        this.message = message;

    }
}
