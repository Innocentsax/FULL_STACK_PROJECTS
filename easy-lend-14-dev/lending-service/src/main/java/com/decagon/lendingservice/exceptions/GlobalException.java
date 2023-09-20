package com.decagon.lendingservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleMessagingException(Exception ex){
        ErrorResponse response = new ErrorResponse();
        response.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setTimestamp(LocalDate.now());
        response.setResponse("An Unexpected Error Occurred");
        return  new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BorrowersNotAllowedException.class)
    public ResponseEntity<ErrorResponse> handleBorrowerNotAllowedException(BorrowersNotAllowedException ex){
        ErrorResponse response = new ErrorResponse();
        response.setErrorCode(HttpStatus.BAD_REQUEST.value());
        response.setTimestamp(LocalDate.now());
        response.setResponse(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = InvestmentPreferenceExistsException.class)
    public  ResponseEntity<ErrorResponse> handleInvestmentPreferenceExistsException(InvestmentPreferenceExistsException ex){
        ErrorResponse response = new ErrorResponse();
        response.setErrorCode(HttpStatus.BAD_REQUEST.value());
        response.setTimestamp(LocalDate.now());
        response.setResponse(ex.getMessage());
        return  new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
