package com.decagon.fitnessoapp.dto.transactionDto.response;

import lombok.Data;

@Data
public class TransVerificationResponse {
    private TransVerifyData data;
    private boolean status;
    private String message;
}
