package com.decagon.fintechpaymentapisqd11a.exceptions;

public class UserAlreadyExistException extends RuntimeException{
    public UserAlreadyExistException(String message) {
        super(message);
    }
}
