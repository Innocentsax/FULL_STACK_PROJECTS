package com.easyLend.userservice.exceptions;

public class AppUserNotFoundExceptions extends RuntimeException{
    public AppUserNotFoundExceptions(String message){
        super(message);
    }
}
