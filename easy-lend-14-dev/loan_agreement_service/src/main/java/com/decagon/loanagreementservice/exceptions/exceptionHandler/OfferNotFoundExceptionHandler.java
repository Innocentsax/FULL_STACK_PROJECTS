package com.decagon.loanagreementservice.exceptions.exceptionHandler;

import com.decagon.loanagreementservice.exceptions.OfferNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class OfferNotFoundExceptionHandler {
    @ExceptionHandler(OfferNotFoundException.class)
    public ResponseEntity<String> handleOfferNotFoundException(OfferNotFoundException ex) {

        String errorMessage = ex.getMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }
}
