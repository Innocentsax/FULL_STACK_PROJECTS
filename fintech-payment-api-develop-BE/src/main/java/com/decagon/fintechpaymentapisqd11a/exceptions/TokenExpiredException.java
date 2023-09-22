package com.decagon.fintechpaymentapisqd11a.exceptions;

public class TokenExpiredException extends RuntimeException {
    public TokenExpiredException(String message) {
        super(message);
    }
}
