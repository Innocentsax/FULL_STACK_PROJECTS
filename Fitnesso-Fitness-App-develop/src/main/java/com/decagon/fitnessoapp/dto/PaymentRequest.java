package com.decagon.fitnessoapp.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PaymentRequest {
    @NotEmpty
    private Long cardNumber;

    @NotEmpty
    private String expiringDate;

    @NotEmpty
    private Integer cvvNumber;
}
