package com.example.hive.exceptions;

public class InvalidRequestException extends RuntimeException{
    protected String message;

    public InvalidRequestException(String message) {
        super(message);
        this.message = message;
    }
}
