package com.decagon.fintechpaymentapisqd11a.exceptions;

public class EmailAlreadyTakenException extends RuntimeException{

    public EmailAlreadyTakenException(String message){
        super(message);
    }
}
