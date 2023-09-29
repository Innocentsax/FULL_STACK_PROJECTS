package com.decagon.OakLandv1be.exceptions;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InsufficientBalanceInWalletException extends RuntimeException{
    private String debugMessage;

    public InsufficientBalanceInWalletException(String message) {

        super(message);
    }

    public InsufficientBalanceInWalletException(String message, String debugMessage) {
        super(message);
        this.debugMessage = debugMessage;
    }
}
