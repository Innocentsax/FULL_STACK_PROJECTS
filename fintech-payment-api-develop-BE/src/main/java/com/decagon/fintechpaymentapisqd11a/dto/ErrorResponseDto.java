package com.decagon.fintechpaymentapisqd11a.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDto {
    private HttpStatus httpStatus;
    private String message;
    private String debugMessage;
    private LocalDateTime time = LocalDateTime.now();

    public ErrorResponseDto(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
