package com.decagon.eventhubbe.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    private SpitPayementResponse data;
    private String message;
    private boolean status;


}
