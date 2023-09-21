package com.decagon.eventhubbe.exception;

public class PaymentExceptionHandler extends RuntimeException{
    public PaymentExceptionHandler(String message){
        super(message);
    }
}
