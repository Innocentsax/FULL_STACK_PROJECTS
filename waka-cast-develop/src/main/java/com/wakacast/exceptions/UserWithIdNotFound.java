package com.wakacast.exceptions;

public class UserWithIdNotFound extends RuntimeException{

    public UserWithIdNotFound(String message) {
        super(message);
    }
}
