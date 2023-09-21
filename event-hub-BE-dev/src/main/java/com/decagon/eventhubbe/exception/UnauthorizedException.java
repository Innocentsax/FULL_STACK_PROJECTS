package com.decagon.eventhubbe.exception;

public class  UnauthorizedException extends RuntimeException{
    public UnauthorizedException(String message) {
        super(message);
    }
}
