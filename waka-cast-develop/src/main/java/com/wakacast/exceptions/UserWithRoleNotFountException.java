package com.wakacast.exceptions;

public class UserWithRoleNotFountException extends RuntimeException{
    public UserWithRoleNotFountException(String message) {
        super(message);
    }
}
