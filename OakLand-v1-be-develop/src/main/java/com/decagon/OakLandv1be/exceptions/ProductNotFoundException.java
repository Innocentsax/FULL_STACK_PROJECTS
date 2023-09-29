package com.decagon.OakLandv1be.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductNotFoundException extends RuntimeException{
    private String debugMessage;

    public ProductNotFoundException(String message) {
        super(message);
    }

    public ProductNotFoundException(String message, String debugMessage) {
        super(message);
        this.debugMessage = debugMessage;
    }
}
