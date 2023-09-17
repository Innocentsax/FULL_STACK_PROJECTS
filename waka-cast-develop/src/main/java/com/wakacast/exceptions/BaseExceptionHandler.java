package com.wakacast.exceptions;

import com.wakacast.responses.ApiResponse;
import com.wakacast.responses.HttpResponse;
import com.wakacast.responses.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.NoResultException;
import javax.security.auth.login.AccountLockedException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class BaseExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseExceptionHandler.class);
    private static final  String ACCOUNT_LOCKED = "Your account has been locked. Please contact administration";
    private static final String INCORRECT_CREDENTIALS = "Username / password incorrect. Please try again";
    private static final String ACCOUNT_DISABLED = "Your account has been locked. If this is an error, please contact administration";
    private static final String ERROR_PROCESSING_FILE = "Error occurred while processing file";
    private static final String NOT_ENOUGH_PERMISSION = "You do not have enough permission";



    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseData handleValidationExceptions(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
                    if (errors.containsKey(error.getField())) {
                        errors.put(error.getField(), String.format("%s, %s", errors.get(error.getField()), error.getDefaultMessage()));
                    } else {
                        errors.put(error.getField(), error.getDefaultMessage());
                    }
                }
        );
        return new ResponseData(new ApiResponse(errors, "VALIDATION_FAILED"));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ResponseData> handleBadRequestException (BadRequestException exception) {
        return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(UserWithEmailNotFound.class)
    public ResponseEntity<ResponseData> handleUserWithEmailNotFoundException (UserWithEmailNotFound exception) {
        return createHttpResponse(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(UserWithIdNotFound.class)
    public ResponseEntity<ResponseData> handleUserWithIdNotFoundException (UserWithIdNotFound exception) {
        return createHttpResponse(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(SizeLimitExceededException.class)
    public ResponseEntity<ResponseData> handleSizeLimitExceededExceptionException (SizeLimitExceededException exception) {
        return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<ResponseData> notFoundException(NoResultException exception) {
        return createHttpResponse(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ResponseData> iOException(IOException exception) {
        LOGGER.error(exception.getMessage());
        return createHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, ERROR_PROCESSING_FILE);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ResponseData> accountDisabledExecution() {
        return createHttpResponse(HttpStatus.FORBIDDEN, ACCOUNT_DISABLED);
    }
    @ExceptionHandler(AccountLockedException.class)
    public ResponseEntity<ResponseData> accountLockedExecution() {
        return createHttpResponse(HttpStatus.FORBIDDEN, ACCOUNT_LOCKED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseData> notEnoughPermissionException() {
        return createHttpResponse(HttpStatus.UNAUTHORIZED, NOT_ENOUGH_PERMISSION);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ResponseData> unauthorizedException() {
        return createHttpResponse(HttpStatus.UNAUTHORIZED, NOT_ENOUGH_PERMISSION);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ResponseData> badCredentialException() {
        return createHttpResponse(HttpStatus.BAD_REQUEST, INCORRECT_CREDENTIALS);
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ResponseData> lockedException() {
        return createHttpResponse(HttpStatus.FORBIDDEN, ACCOUNT_DISABLED);
    }

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ResponseData> resourceNotFoundException(ResourceNotFound exception) {
        return createHttpResponse(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    private ResponseEntity<ResponseData> createHttpResponse(HttpStatus httpStatus, String message) {
        HttpResponse httpResponse = new HttpResponse(httpStatus.value(),
                httpStatus, httpStatus.getReasonPhrase().toUpperCase(), message);
        return new ResponseEntity<>(new ResponseData(httpResponse), httpStatus);
    }
}
