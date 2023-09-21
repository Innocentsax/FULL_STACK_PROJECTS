package com.decagon.eventhubbe.exception;

public class  AppUserNotFoundException extends RuntimeException{
    public AppUserNotFoundException(String message) {
        super(message);
    }
}
