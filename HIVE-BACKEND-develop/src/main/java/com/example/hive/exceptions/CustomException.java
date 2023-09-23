package com.example.hive.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@Getter
public class CustomException extends RuntimeException{

    protected String message;
    protected HttpStatus status;

    public CustomException(String message) {
        super(message);
        this.message = message;
    }

    public CustomException(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
