package com.example.hive.exceptions;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;


@Getter
@Setter
@Builder
public class BadRequestException extends RuntimeException{

    protected String message;
    protected HttpStatus status = HttpStatus.BAD_REQUEST;

    public BadRequestException(String message) {
        super(message);
        this.message = message;
    }

    public BadRequestException(String message, HttpStatus status) {
        super(message);
        this.message = message;
        this.status = status;
    }
}