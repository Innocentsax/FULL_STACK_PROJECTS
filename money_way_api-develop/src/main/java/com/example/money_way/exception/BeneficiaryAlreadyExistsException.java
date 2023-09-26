package com.example.money_way.exception;

public class BeneficiaryAlreadyExistsException extends RuntimeException {
    public BeneficiaryAlreadyExistsException (String message)  {
        super(message);
    };
}
