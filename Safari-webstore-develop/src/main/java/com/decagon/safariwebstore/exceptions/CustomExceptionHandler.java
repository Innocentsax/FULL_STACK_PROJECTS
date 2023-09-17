package com.decagon.safariwebstore.exceptions;

import com.decagon.safariwebstore.utils.MethodUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<String> resourceNotFoundException(ResourceNotFoundException exception){
        HttpStatus status = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(MethodUtils.prepareErrorJSON(status, exception), status);
    }

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<String> badRequestException(BadRequestException exception){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(MethodUtils.prepareErrorJSON(status, exception), status);
    }
}
