package com.goCash.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.http.HttpStatus;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@Builder
@ToString
public class ApiResponse <T> {

    private String code;
    private String message;
    private T data;

    @JsonIgnore
    private HttpStatus httpStatus;



    public ApiResponse(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ApiResponse(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
