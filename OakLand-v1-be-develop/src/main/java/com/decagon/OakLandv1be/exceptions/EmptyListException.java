package com.decagon.OakLandv1be.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmptyListException extends RuntimeException{
    private String debugMessage;

    public EmptyListException(String message, String debugMessage) {
        super(message);
        this.debugMessage = debugMessage;
    }

    public EmptyListException(String message) {

        super(message);
    }
}
