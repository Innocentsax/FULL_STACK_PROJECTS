package com.decagon.OakLandv1be.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PickupCenterNotFoundException extends RuntimeException{
    private String debugMessage;

    public PickupCenterNotFoundException(String message) {

        super(message);
    }

    public PickupCenterNotFoundException(String message, String debugMessage) {
        super(message);
        this.debugMessage = debugMessage;
    }
}
