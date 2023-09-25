package com.decadev.money.way.exception;

import lombok.Data;

@Data
public class UserAlreadyExistsException extends Exception{

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
