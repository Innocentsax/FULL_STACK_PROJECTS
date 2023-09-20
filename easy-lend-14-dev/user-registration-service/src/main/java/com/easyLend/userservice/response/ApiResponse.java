package com.easyLend.userservice.response;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ApiResponse<T> {
    private T data;
    private String message;
    private LocalDateTime localDateTime;

    public ApiResponse(T data){
        this.data = data;
        this.message = "response received";
        this.localDateTime=LocalDateTime.now();

    }
}
