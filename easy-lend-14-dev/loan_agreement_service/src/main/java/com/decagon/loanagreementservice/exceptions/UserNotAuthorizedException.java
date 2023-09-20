package com.decagon.loanagreementservice.exceptions;

public class UserNotAuthorizedException extends RuntimeException{
    public UserNotAuthorizedException(String message){
        super (message);
    }
}
