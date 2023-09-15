package com.example.cedarxpressliveprojectjava010.exception;


import lombok.extern.slf4j.Slf4j;

import com.example.cedarxpressliveprojectjava010.response.ApiResponse;





import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;


import java.util.Date;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundEx(UserNotFoundException ex, WebRequest request){
        ApiErrorDetail errorDetail = new ApiErrorDetail(ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetail,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<?> handeleProductNotFoundEX(ProductNotFoundException ex, WebRequest request){
        ApiErrorDetail errorDetail = new ApiErrorDetail(ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetail,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<?> handleFileNotFoundEX(FileNotFoundException ex, WebRequest request){
        ApiErrorDetail errorDetail = new ApiErrorDetail(ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetail,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<?> handleIncorrectPasswordEX(IncorrectPasswordException ex, WebRequest request){
        ApiErrorDetail errorDetail = new ApiErrorDetail(ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetail,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<?> handleCartNotFoundEX(CartNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(new ApiResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsEx(BadCredentialsException ex, WebRequest request){
        ApiErrorDetail errorDetail = new ApiErrorDetail(ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetail,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(NotFoundException e, WebRequest request){
        return handleException(e, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {ClientRequestException.class})
    public ResponseEntity<Object> handleClientRequestException(ClientRequestException e, WebRequest request){
        return handleException(e, request, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = {ServerException.class})
    public ResponseEntity<Object> handleServerException(ServerException e, WebRequest request){
        return handleException(e, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleGeneralException(Exception e, WebRequest request){
        return handleException(e, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(value = {CartEmptyException.class})
    public ResponseEntity<Object> handleCartEmptyException(CartEmptyException e, WebRequest request){
        return handleException(e, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Object> handleException(Exception e, WebRequest request, HttpStatus httpStatus){
        log.error("API error: ", e);
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Date(),
                e.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, httpStatus);
    }

}

