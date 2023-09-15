package com.example.cedarxpressliveprojectjava010.exception;

public class IncorrectPasswordException extends RuntimeException{
    public IncorrectPasswordException(String message){
        super(message);
    }
}
