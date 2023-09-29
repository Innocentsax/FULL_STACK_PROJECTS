package com.decagon.OakLandv1be.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailNotFoundException extends RuntimeException{
    private String debugMessage;

    public EmailNotFoundException(String message) {

        super(message);
    }

    public EmailNotFoundException(String message, String debugMessage) {

        super(message);
        this.debugMessage = debugMessage;
    }
}
