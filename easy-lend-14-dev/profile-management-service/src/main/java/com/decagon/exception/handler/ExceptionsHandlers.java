package com.decagon.exception.handler;

import com.decagon.dto.response.ApiResponse;
import com.decagon.exception.BankNotFoundException;
import com.decagon.exception.EasyLendExceptionResponse;
import com.decagon.exception.ProfileNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionsHandlers {

    @ExceptionHandler(ProfileNotFoundException.class)
    public ResponseEntity<ApiResponse<EasyLendExceptionResponse>> profileNotFound(ProfileNotFoundException e){

        EasyLendExceptionResponse exceptionResponse = EasyLendExceptionResponse.builder()
                .time(saveLocalDate(LocalDateTime.now()))
                .message(e.getMessage())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .build();
        ApiResponse<EasyLendExceptionResponse> apiResponse = new ApiResponse<>(exceptionResponse);
        return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(BankNotFoundException.class)
    public ResponseEntity<ApiResponse<EasyLendExceptionResponse>> bankNotfound(BankNotFoundException e){

        EasyLendExceptionResponse exceptionResponse = EasyLendExceptionResponse.builder()
                .time(saveLocalDate(LocalDateTime.now()))
                .message(e.getMessage())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .build();
        ApiResponse<EasyLendExceptionResponse> apiResponse = new ApiResponse<>(exceptionResponse);
        return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
    }
    private String saveLocalDate(LocalDateTime localDateTime){
        return localDateTime.toString();

    }


}
