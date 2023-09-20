package com.decagon.loanagreementservice.exceptions;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String invalidToken) {
        super(invalidToken);
    }
}
