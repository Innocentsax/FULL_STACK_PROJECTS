package com.example.hive.exceptions;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ErrorDetails implements Serializable {

    private String message;
    private String details;
    private int code;
    private List<ValidationError> validation;

    public ErrorDetails(LocalDate timestamp, String message, String details, int code) {
        this.message = message;
        this.details = details;
        this.code = code;
    }

    public ErrorDetails(LocalDate timestamp, String message, String details, int code, List<ValidationError> validation) {
        this.message = message;
        this.details = details;
        this.code = code;
        this.validation = validation;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class ValidationError implements Serializable {

        private Object rejectedValue;
        private String field;
        private String objectName;
    }
}
