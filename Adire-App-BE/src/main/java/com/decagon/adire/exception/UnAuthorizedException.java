package com.decagon.adire.exception;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@Component
public class UnAuthorizedException extends RuntimeException{
    private String message;
    private HttpStatus status;

    public UnAuthorizedException( String message) {
        this.message = message;
    }
    public UnAuthorizedException( String message,HttpStatus status) {
        this.status = status;
        this.message = message;
    }
}
