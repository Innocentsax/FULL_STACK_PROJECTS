package com.decagon.adire.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AppResponse<T> {

    private String message;
    private HttpStatus statusCode;
    private boolean status;
    private final LocalDateTime time = LocalDateTime.now();
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public AppResponse(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

    public static  <T> AppResponse<T> buildSuccessTxn(T data) {
        AppResponse<T> response = new AppResponse<>();
        response.setStatusCode(HttpStatus.OK);
        response.setMessage("Success");
        response.setStatus(true);
        response.setData(data);
        return response;
    }

    public static  <T> AppResponse<T> buildSuccess(T data) {
        AppResponse<T> response = new AppResponse<>();
        response.setStatusCode(HttpStatus.CREATED);
        response.setMessage("Success");
        response.setStatus(true);
        response.setData(data);
        return response;
    }

    public static AppResponse<Void> build() {
        AppResponse<Void> response = new AppResponse<>();
        response.setMessage("Success");
        return response;
    }

}