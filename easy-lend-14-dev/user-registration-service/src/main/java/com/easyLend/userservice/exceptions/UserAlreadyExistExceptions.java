package com.easyLend.userservice.exceptions;

public class UserAlreadyExistExceptions extends RuntimeException {
    public UserAlreadyExistExceptions(String message){
        super(message);
    }

}
