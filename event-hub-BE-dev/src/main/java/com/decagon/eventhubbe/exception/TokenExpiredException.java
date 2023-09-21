package com.decagon.eventhubbe.exception;

public class TokenExpiredException extends RuntimeException{
    public  TokenExpiredException(String message){
        super(message);
    }

}
