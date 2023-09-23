package com.example.hive.exceptions;

import com.example.hive.constant.ResponseStatus;
import org.springframework.http.HttpStatus;

public class InsufficientBalanceException extends RuntimeException{
    protected String message;
    protected HttpStatus status;
    protected final String statusCode = ResponseStatus.INSUFFICIENT_FUNDS.getCode();


    public InsufficientBalanceException(String message) {
        super(message);
        this.message = message;
    }

    public InsufficientBalanceException(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
