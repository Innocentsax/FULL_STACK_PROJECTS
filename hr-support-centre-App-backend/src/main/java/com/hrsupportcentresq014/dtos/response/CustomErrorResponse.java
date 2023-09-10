package com.hrsupportcentresq014.dtos.response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomErrorResponse {
    private String message;
    private LocalDateTime timeStamp;
    private HttpStatus statusCode;
}
