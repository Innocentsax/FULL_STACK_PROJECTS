package com.goCash.exception;

import com.goCash.utils.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
@Slf4j
@RestControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponse> handleUserNotFoundException(UserNotFoundException exception) {
        log.info("Please dont break my application");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder()
                .message(exception.getMessage())
                .build());
    }

    @ExceptionHandler(PasswordMatcherException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponse> handlePasswordMatcherException(PasswordMatcherException exception) {
        log.info("I only come up if the 2 passwords provided by the user is dagbo");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder()
                .message(exception.getMessage())
                .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ApiResponse> handleGlobalExceptions(MethodArgumentNotValidException ex) {
        String[] errors = ex.getBindingResult().getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toArray(String[]::new);
        ApiResponse response = ApiResponse.builder()
                .code("00")
                .message(Arrays.toString(errors))
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(WalletNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponse> WalletNotFoundException(PasswordMatcherException exception) {
        log.info("Wallet is for logged in users");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.builder()
                .message(exception.getMessage())
                .build());

    }
}