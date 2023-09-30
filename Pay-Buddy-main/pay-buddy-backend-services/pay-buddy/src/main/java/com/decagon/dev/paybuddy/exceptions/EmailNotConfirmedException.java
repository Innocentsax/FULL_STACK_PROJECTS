package com.decagon.dev.paybuddy.exceptions;

/**
 * @author Ikechi Ucheagwu
 * @created 17/02/2023 - 17:38
 * @project Pay-Buddy
 */

public class EmailNotConfirmedException extends RuntimeException{
    public EmailNotConfirmedException(String message) {
        super(message);
    }
}
