package com.decagon.OakLandv1be.exceptions;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private String message;
    private String debugMessage;
    private HttpStatus status;
}
