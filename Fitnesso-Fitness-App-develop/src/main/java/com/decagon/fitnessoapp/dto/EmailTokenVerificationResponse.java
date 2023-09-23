package com.decagon.fitnessoapp.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailTokenVerificationResponse {

    private String message;
}
