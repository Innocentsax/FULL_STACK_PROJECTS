package com.decagon.adire.exception;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@NoArgsConstructor
public class CustomException extends RuntimeException{
    private String message;
    private HttpStatus status;

    public CustomException( String message) {
        this.message = message;
    }
    public CustomException( String message,HttpStatus status) {
        this.status = status;
        this.message = message;
    }

}
