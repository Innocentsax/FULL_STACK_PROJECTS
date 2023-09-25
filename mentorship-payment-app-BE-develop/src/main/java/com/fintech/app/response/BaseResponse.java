package com.fintech.app.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class BaseResponse<T> {

    private HttpStatus status;
    private String message;
    private T result;

    public BaseResponse() {
    }

    public BaseResponse(HttpStatus status, String message, T result) {
        this.status = status;
        this.message = message;
        this.result = result;
    }
}
