package com.easyLend.userservice.exceptions;

public class PasswordNotFoundException extends RuntimeException{
    public PasswordNotFoundException(String message){
        super(message);
    }
}
