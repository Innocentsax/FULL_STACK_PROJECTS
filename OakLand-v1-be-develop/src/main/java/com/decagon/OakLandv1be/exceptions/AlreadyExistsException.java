package com.decagon.OakLandv1be.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlreadyExistsException extends RuntimeException{

    private String debugMessage;

    public AlreadyExistsException(String message) {

        super(message);
    }

    public AlreadyExistsException(String message, String debugMessage) {
        super(message);
        this.debugMessage = debugMessage;
    }



}

