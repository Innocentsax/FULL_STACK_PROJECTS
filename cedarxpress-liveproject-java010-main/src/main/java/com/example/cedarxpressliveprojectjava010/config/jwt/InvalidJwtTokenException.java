package com.example.cedarxpressliveprojectjava010.config.jwt;

import org.springframework.http.HttpStatus;

public class InvalidJwtTokenException extends RuntimeException {
    public InvalidJwtTokenException(HttpStatus status, String error) {
        super(error);
    }
}
