package com.decagon.exception;

public class BankNotFoundException extends RuntimeException{
    public BankNotFoundException(String message){
        super(message);
    }
}
