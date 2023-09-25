package com.decadev.money.way.exception;

import com.decadev.money.way.dto.response.ErrorResponse;
import com.decadev.money.way.dto.response.ExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRoleNotFoundException(UserNotFoundException ex){
        ErrorResponse response = ErrorResponse.builder()
                                                        .status(HttpStatus.NOT_FOUND)
                                                        .timeStamp(LocalDateTime.now())
                                                        .message(ex.getMessage()).build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(value = UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleRoleNotFoundException(UserAlreadyExistsException ex){
        ErrorResponse response = ErrorResponse.builder()
                                                        .status(HttpStatus.CONFLICT)
                                                        .timeStamp(LocalDateTime.now())
                                                        .message(ex.getMessage()).build();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(value = UserAccountDisabledException.class)
    public ResponseEntity<ErrorResponse> handleRoleNotFoundException(UserAccountDisabledException ex){
        ErrorResponse response = ErrorResponse.builder()
                                                        .status(HttpStatus.CONFLICT)
                                                        .timeStamp(LocalDateTime.now())
                                                        .message(ex.getMessage()).build();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e
    ){
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error)->{
            String fieldName = ((FieldError)error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler
    public ResponseEntity<ErrorResponse> genericHandler(Exception ex, HttpServletRequest request){
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(),LocalDateTime.now(), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(TransactionException.class)
    public ResponseEntity<ExceptionResponse> transactionNotFound(TransactionException e, HttpServletRequest request){
        ExceptionResponse er = ExceptionResponse.builder()
                .errorMessage(e.getMessage())
                .errorPath(request.getRequestURI())
                .errorStatusCode(HttpStatus.NOT_FOUND)
                .errorTime(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(er,HttpStatus.NOT_FOUND);
    }

}
