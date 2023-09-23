package com.decagon.fitnessoapp.exception;

public class PersonNotFoundException extends RuntimeException{

    public PersonNotFoundException(String message) {
        super(message);
    }
}
