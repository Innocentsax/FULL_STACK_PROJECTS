package com.decagon.borrowerservice.exception.handler;

import com.decagon.borrowerservice.exception.BorrowerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class LoanExceptionsHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler (BorrowerNotFoundException.class)
    public ResponseEntity<Object> handleLoanNotFoundException (BorrowerNotFoundException ex, WebRequest request) {
        logger.error(ex.getMessage(), ex);
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timeStamp", LocalDateTime.now());
        body.put("message", ex.getMessage());
        return new ResponseEntity<Object>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler (IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalLoanNotFoundException (IllegalArgumentException ex, WebRequest request) {
        logger.error(ex.getMessage(), ex);
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timeStamp", LocalDateTime.now());
        body.put("message", ex.getMessage());
        return new ResponseEntity<Object>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler (UserNotAuthorizedException.class)
    public ResponseEntity<Object> handleUserNotAuthorizeException (UserNotAuthorizedException ex, WebRequest request) {
        logger.error(ex.getMessage(), ex);
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timeStamp", LocalDateTime.now());
        body.put("message", ex.getMessage());
        return new ResponseEntity<Object>(body, HttpStatus.UNAUTHORIZED);
    }
}
