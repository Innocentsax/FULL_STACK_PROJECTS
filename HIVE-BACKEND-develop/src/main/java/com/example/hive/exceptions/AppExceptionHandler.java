package com.example.hive.exceptions;


import com.example.hive.dto.response.AppResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;

@Log4j2
@RestControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<AppResponse<Object>> handleGlobalExceptions(MethodArgumentNotValidException ex,
                                                                         WebRequest request) {
        String[] errors = ex.getBindingResult().getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toArray(String[]::new);

        AppResponse<Object> response = AppResponse.builder()
                .isSuccessful(false)
                .message("An error occurred, check message below")
                .result(errors)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(value = {CustomException.class})
    public ResponseEntity<AppResponse<?>> handleCustomException(CustomException exception) {
        log.error("custom exception:: msg: {}",exception.getMessage());

        AppResponse<Object> response = AppResponse.builder()
                .isSuccessful(false)
                .message("An error occurred, check message below")
                .result(exception.getMessage())
                .build();

        return ResponseEntity.status(exception.getStatus()).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<AppResponse<Object>> globalExceptionHandler(Exception ex, WebRequest request) {
        ex.printStackTrace();

        AppResponse<Object> response = AppResponse.builder()
                .isSuccessful(false)
                .message("An error occurred, check message below")
                .result(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<AppResponse<ErrorDetails>> handleAdminNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        int statusCode = ex.getStatus().value() != 0 ? ex.getStatus().value() : HttpStatus.PRECONDITION_FAILED.value();
        ErrorDetails errorDetails = ErrorDetails.builder()
                .message(ex.getMessage())
                .code(statusCode)
                .details(request.getDescription(true))
                .build();
        return ResponseEntity.status(errorDetails.getCode()).body(AppResponse.<ErrorDetails>builder()
                .statusCode(HttpStatus.valueOf(statusCode).toString())
                .error(errorDetails)
                .build());
    }

    @ExceptionHandler(ResourceCreationException.class)
    public final ResponseEntity<AppResponse<ErrorDetails>> handleResourceCreationException(ResourceCreationException ex, WebRequest request) {
        int statusCode = ex.getStatus().value() != 0 ? ex.getStatus().value() : HttpStatus.PRECONDITION_FAILED.value();
        ErrorDetails errorDetails = ErrorDetails.builder()
                .message(ex.getMessage())
                .code(statusCode)
                .details(request.getDescription(true))
                .build();
        return ResponseEntity.status(errorDetails.getCode()).body(AppResponse.<ErrorDetails>builder()
                .statusCode(HttpStatus.valueOf(statusCode).toString())
                .error(errorDetails)
                .build());
    }


    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<AppResponse<ErrorDetails>> handleBadRequestException(BadRequestException ex, WebRequest request) {
        int statusCode = ex.getStatus().value() != 0 ? ex.getStatus().value() : HttpStatus.PRECONDITION_FAILED.value();
        ErrorDetails errorDetails = ErrorDetails.builder()
                .message(ex.getMessage())
                .code(statusCode)
                .details(request.getDescription(true))
                .build();
        return ResponseEntity.status(errorDetails.getCode()).body(AppResponse.<ErrorDetails>builder()
                .statusCode(HttpStatus.valueOf(statusCode).toString())
                .error(errorDetails)
                .build());
    }
}



