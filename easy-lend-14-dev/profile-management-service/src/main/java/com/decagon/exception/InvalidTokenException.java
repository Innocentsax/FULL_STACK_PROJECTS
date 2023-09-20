package com.decagon.exception;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String invalidToken) {
        super(invalidToken);
    }
}
