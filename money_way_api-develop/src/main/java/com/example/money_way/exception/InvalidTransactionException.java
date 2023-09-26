package com.example.money_way.exception;


public class InvalidTransactionException extends RuntimeException {

    private String message;

    public InvalidTransactionException() {
        super();
        message = "Invalid transaction";
    }

    public InvalidTransactionException(String message) {
        super(message);
        this.message = message;
    }
}
