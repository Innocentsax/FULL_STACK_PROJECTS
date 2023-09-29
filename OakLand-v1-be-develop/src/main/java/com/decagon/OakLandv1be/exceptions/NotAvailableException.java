package com.decagon.OakLandv1be.exceptions;

public class NotAvailableException extends RuntimeException{
    private String debugMessage;

    public NotAvailableException(String message) {
        super(message);
    }

    public NotAvailableException(String message, String debugMessage) {
        super(message);
        this.debugMessage = debugMessage;
    }
}
