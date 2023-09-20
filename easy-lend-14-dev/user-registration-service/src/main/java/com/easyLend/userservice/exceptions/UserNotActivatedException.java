package com.easyLend.userservice.exceptions;

public class UserNotActivatedException extends RuntimeException{

    public UserNotActivatedException(String message){
        super(message);
    }
}
