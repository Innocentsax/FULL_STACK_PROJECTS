package com.decagon.dev.paybuddy.utilities;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
@Builder
public class ErrorResponse {
    private int httpStatusCode;
    private List<Object> message;
    private LocalDateTime responseDate;
}
