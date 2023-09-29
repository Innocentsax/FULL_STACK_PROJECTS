package com.decagon.OakLandv1be.exceptions;

public class InvalidOperationException extends RuntimeException {
    private String debugMessage;
    public InvalidOperationException(String message) {
        super(message);
        this.debugMessage=message;
    }
}
