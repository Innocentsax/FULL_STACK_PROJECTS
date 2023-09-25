package com.decadev.money.way.exception;

import lombok.Data;

@Data
public class TransactionException extends RuntimeException{
    public TransactionException(String message){
        super(message);
    }
}
