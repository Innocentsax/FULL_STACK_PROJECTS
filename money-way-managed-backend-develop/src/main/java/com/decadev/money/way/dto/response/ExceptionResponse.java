package com.decadev.money.way.dto.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class ExceptionResponse {
    private LocalDateTime errorTime;
    private String errorMessage;
    private String errorPath;
    private HttpStatus errorStatusCode;
}
