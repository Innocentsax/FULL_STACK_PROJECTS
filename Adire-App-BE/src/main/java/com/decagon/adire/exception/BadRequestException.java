package com.decagon.adire.exception;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
public class BadRequestException extends RuntimeException{
    private HttpStatus status;
    private String message;

    public BadRequestException(String message) {
        this.message = message;
    }

    public BadRequestException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
