package com.decagon.dev.paybuddy.exceptions;

public class IncorrectMerchantIdentity extends RuntimeException{

    public IncorrectMerchantIdentity(String message) {
        super(message);
    }
}
