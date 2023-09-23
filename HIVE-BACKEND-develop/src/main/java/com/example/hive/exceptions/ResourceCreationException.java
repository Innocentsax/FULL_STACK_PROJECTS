package com.example.hive.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@Getter
public class ResourceCreationException extends RuntimeException{

    protected String message;
    protected HttpStatus status = HttpStatus.BAD_REQUEST;
    public ResourceCreationException(String message) {
        super(message);
        this.message = message;
    }
}
