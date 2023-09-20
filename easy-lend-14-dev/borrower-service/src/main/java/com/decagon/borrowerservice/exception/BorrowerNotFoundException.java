package com.decagon.borrowerservice.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class BorrowerNotFoundException extends RuntimeException{

    public BorrowerNotFoundException(String message){super(message);
    }
}