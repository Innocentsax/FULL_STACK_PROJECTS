package com.example.hive.exceptions;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
public class ResourceNotFoundException extends RuntimeException{

    protected String message;
    protected HttpStatus status = HttpStatus.FOUND;

    public ResourceNotFoundException(String message) {
        super(message);
        this.message = message;
    }
    public ResourceNotFoundException(String message, HttpStatus status) {
        super(message);
        this.status = status;
        this.message = message;
    }
}
