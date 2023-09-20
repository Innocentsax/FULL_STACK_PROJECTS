package com.easyLend.userservice.exceptions.handler;

import com.easyLend.userservice.exceptions.*;
import com.easyLend.userservice.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionsHandlers {

    @ExceptionHandler(AppUserNotFoundExceptions.class)
    public ResponseEntity<ApiResponse<EasyLendExceptionResponse>> userNotFound(AppUserNotFoundExceptions e){

        EasyLendExceptionResponse exceptionResponse = EasyLendExceptionResponse.builder()
                .time(saveLocalDate(LocalDateTime.now()))
                .message(e.getMessage())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .build();
        ApiResponse<EasyLendExceptionResponse> apiResponse = new ApiResponse<>(exceptionResponse);
        return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistExceptions.class)
    public ResponseEntity<ApiResponse<EasyLendExceptionResponse>> userAlreadyExist(UserAlreadyExistExceptions e){

        EasyLendExceptionResponse exceptionResponse = EasyLendExceptionResponse.builder()
                .time(saveLocalDate(LocalDateTime.now()))
                .message(e.getMessage())
                .statusCode(HttpStatus.CONFLICT.value())
                .build();
        ApiResponse<EasyLendExceptionResponse> apiResponse = new ApiResponse<>(exceptionResponse);
        return new ResponseEntity<>(apiResponse,HttpStatus.CONFLICT);
    }
    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<ApiResponse<EasyLendExceptionResponse>> tokenNotfound(TokenNotFoundException e){

        EasyLendExceptionResponse exceptionResponse = EasyLendExceptionResponse.builder()
                .time(saveLocalDate(LocalDateTime.now()))
                .message(e.getMessage())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .build();
        ApiResponse<EasyLendExceptionResponse> apiResponse = new ApiResponse<>(exceptionResponse);
        return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(PasswordNotFoundException.class)
    public ResponseEntity<ApiResponse<EasyLendExceptionResponse>> passwordNotfound(PasswordNotFoundException e){

        EasyLendExceptionResponse exceptionResponse = EasyLendExceptionResponse.builder()
                .time(saveLocalDate(LocalDateTime.now()))
                .message(e.getMessage())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .build();
        ApiResponse<EasyLendExceptionResponse> apiResponse = new ApiResponse<>(exceptionResponse);
        return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotActivatedException.class)
    public ResponseEntity<ApiResponse<EasyLendExceptionResponse>> userNotaCTIVATED(UserNotActivatedException e){

        EasyLendExceptionResponse exceptionResponse = EasyLendExceptionResponse.builder()
                .time(saveLocalDate(LocalDateTime.now()))
                .message(e.getMessage())
                .statusCode(HttpStatus.LOCKED.value())
                .build();
        ApiResponse<EasyLendExceptionResponse> apiResponse = new ApiResponse<>(exceptionResponse);
        return new ResponseEntity<>(apiResponse,HttpStatus.LOCKED);
    }





    private String saveLocalDate(LocalDateTime localDateTime){
        return localDateTime.toString();

    }


}
