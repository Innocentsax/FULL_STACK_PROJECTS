package com.decagon.eventhubbe.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class  EventHubExceptionResponse {
    private String time;
    private String message;
    private String path;
    private Integer statusCode;
}
