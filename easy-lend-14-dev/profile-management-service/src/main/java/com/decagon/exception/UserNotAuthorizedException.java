package com.decagon.exception;

public class UserNotAuthorizedException extends RuntimeException{
    public UserNotAuthorizedException(String message){
        super (message);
    }
}
