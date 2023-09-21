package com.decagon.eventhubbe.exception;

import com.decagon.eventhubbe.dto.response.APIResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice
public class EventHubExceptionHandler {
    @ExceptionHandler(AppUserNotFoundException.class)
    public ResponseEntity<APIResponse<EventHubExceptionResponse>> userNotFound(AppUserNotFoundException e,
                                                    HttpServletRequest request){
        EventHubExceptionResponse exceptionResponse = EventHubExceptionResponse.builder()
                .time(saveLocalDate(LocalDateTime.now()))
                .message(e.getMessage())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .path(request.getRequestURI())
                .build();
        APIResponse<EventHubExceptionResponse> apiResponse = new APIResponse<>(exceptionResponse);
        return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(AppUserAlreadyExistException.class)
    public ResponseEntity<APIResponse<EventHubExceptionResponse>> userAlreadyExist(AppUserAlreadyExistException e,
                                                                  HttpServletRequest request){
        EventHubExceptionResponse exceptionResponse = EventHubExceptionResponse.builder()
                .time(saveLocalDate(LocalDateTime.now()))
                .message(e.getMessage())
                .statusCode(HttpStatus.CONFLICT.value())
                .path(request.getRequestURI())
                .build();
            APIResponse<EventHubExceptionResponse> apiResponse = new APIResponse<>(exceptionResponse);
        return new ResponseEntity<>(apiResponse,HttpStatus.CONFLICT);
    }
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<APIResponse<EventHubExceptionResponse>>  unauthorized(UnauthorizedException e,
                                                                      HttpServletRequest request){
        EventHubExceptionResponse exceptionResponse = EventHubExceptionResponse.builder()
                .time(saveLocalDate(LocalDateTime.now()))
                .message(e.getMessage())
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .path(request.getRequestURI())
                .build();
        APIResponse<EventHubExceptionResponse> apiResponse = new APIResponse<>(exceptionResponse);

        return new ResponseEntity<>(apiResponse,HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<APIResponse<EventHubExceptionResponse>> invalidCredentials(InvalidCredentialsException e,
                                                                      HttpServletRequest request){
        EventHubExceptionResponse exceptionResponse = EventHubExceptionResponse.builder()
                .time(saveLocalDate(LocalDateTime.now()))
                .message(e.getMessage())
                .statusCode(HttpStatus.CONFLICT.value())
                .path(request.getRequestURI())
                .build();
        APIResponse<EventHubExceptionResponse> apiResponse = new APIResponse<>(exceptionResponse);
        return new ResponseEntity<>(apiResponse,HttpStatus.CONFLICT);
    }
    @ExceptionHandler(SamePasswordException.class)
    public ResponseEntity<APIResponse<EventHubExceptionResponse>> samePassword(SamePasswordException e,
                                                                      HttpServletRequest request){
        EventHubExceptionResponse exceptionResponse = EventHubExceptionResponse.builder()
                .time(saveLocalDate(LocalDateTime.now()))
                .message(e.getMessage())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .path(request.getRequestURI())
                .build();
        APIResponse<EventHubExceptionResponse> apiResponse = new APIResponse<>(exceptionResponse);
        return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UserDisabledException.class)
    public ResponseEntity<APIResponse<EventHubExceptionResponse>> userDisabled(UserDisabledException e,
                                                                      HttpServletRequest request){
        EventHubExceptionResponse exceptionResponse = EventHubExceptionResponse.builder()
                .time(saveLocalDate(LocalDateTime.now()))
                .message(e.getMessage())
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .path(request.getRequestURI())
                .build();
        APIResponse<EventHubExceptionResponse> apiResponse =new APIResponse<>(exceptionResponse);
        return new ResponseEntity<>(apiResponse,HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(AccountIsuesException.class)
    public ResponseEntity<APIResponse<EventHubExceptionResponse>> accountError(AccountIsuesException e,HttpServletRequest request){
        EventHubExceptionResponse exceptionResponse = EventHubExceptionResponse.builder()
                .message(e.getMessage())
                .path(request.getRequestURI())
                .statusCode(HttpStatus.CONFLICT.value())
                .time(saveLocalDate(LocalDateTime.now()))
                .build();
        APIResponse<EventHubExceptionResponse> apiResponse = new APIResponse<>(exceptionResponse);
        return new ResponseEntity<>(apiResponse,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<APIResponse<EventHubExceptionResponse>> tokenNotFound(TokenNotFoundException e,
                                                                                HttpServletRequest request){
        EventHubExceptionResponse exceptionResponse = EventHubExceptionResponse.builder()
                .time(saveLocalDate(LocalDateTime.now()))
                .message(e.getMessage())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .path(request.getRequestURI())
                .build();
        APIResponse<EventHubExceptionResponse> apiResponse =new APIResponse<>(exceptionResponse);
        return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<APIResponse<EventHubExceptionResponse>> tokenExpired(TokenExpiredException e,
                                                                                HttpServletRequest request){
        EventHubExceptionResponse exceptionResponse = EventHubExceptionResponse.builder()
                .time(saveLocalDate(LocalDateTime.now()))
                .message(e.getMessage())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .path(request.getRequestURI())
                .build();
        APIResponse<EventHubExceptionResponse> apiResponse =new APIResponse<>(exceptionResponse);
        return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
    }
    private String saveLocalDate(LocalDateTime date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd h:mm:ss a");
        return date.format(formatter);
    }
}
