package com.decagon.loanagreementservice.exceptions.exceptionHandler;

import com.decagon.loanagreementservice.exceptions.AgreementNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AgreementExceptionHandler {
    @ExceptionHandler(AgreementNotFoundException.class)
    public ResponseEntity<String> handleAgreementNotFoundException(AgreementNotFoundException ex) {

        String errorMessage = ex.getMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }
}
