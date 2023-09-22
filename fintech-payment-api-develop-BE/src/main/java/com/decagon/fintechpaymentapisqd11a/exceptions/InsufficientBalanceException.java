package com.decagon.fintechpaymentapisqd11a.exceptions;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}
