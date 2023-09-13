package com.decagon.adire.exception;
import com.decagon.adire.dto.response.AppResponse;
import com.decagon.adire.utils.AppUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionControllerHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<AppResponse> userNotFound(NotFoundException exception){
        return AppUtil.errorResponse(HttpStatus.NOT_FOUND, exception.getMessage());
    }
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<AppResponse> customException(CustomException exception){
        return AppUtil.errorResponse(HttpStatus.FORBIDDEN, exception.getMessage());
    }

    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<AppResponse> unAuthorizedException(UnAuthorizedException exception) {
        return AppUtil.errorResponse(HttpStatus.UNAUTHORIZED, exception.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<AppResponse> badRequestExpection(BadRequestException exception) {
        return AppUtil.errorResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

}
