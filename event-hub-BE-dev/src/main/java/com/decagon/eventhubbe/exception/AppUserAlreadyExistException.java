package com.decagon.eventhubbe.exception;

public class AppUserAlreadyExistException extends RuntimeException{
    public AppUserAlreadyExistException(String email) {
        super("User "+email+ " already registered");
    }
}
