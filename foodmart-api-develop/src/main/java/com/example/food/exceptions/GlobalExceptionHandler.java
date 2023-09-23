package com.example.food.exceptions;

import com.example.food.Enum.ResponseCodeEnum;
import com.example.food.restartifacts.BaseResponse;
import com.example.food.util.ResponseCodeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends RuntimeException{

    private ResponseCodeUtil responseCodeUtil = new ResponseCodeUtil();


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<BaseResponse> userNotFound(UserNotFoundException userNotFoundException) {
        BaseResponse response = new BaseResponse();
        responseCodeUtil.updateResponseData(response, ResponseCodeEnum.USER_NOT_FOUND, userNotFoundException.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
