package com.example.money_way.exception;

public class MailSendingException extends RuntimeException {

    public MailSendingException(String errorMessage) {
        super(errorMessage);
    }
}
