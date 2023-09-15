package com.example.cedarxpressliveprojectjava010.exception;

public class BadCredentialsException extends IllegalArgumentException{
    public BadCredentialsException(String message){
        super(message);
    }
}
