package com.decagon.fitnessoapp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CheckOutResponse {
    private String message;
    protected LocalDateTime timestamp;
    private String link;
}
