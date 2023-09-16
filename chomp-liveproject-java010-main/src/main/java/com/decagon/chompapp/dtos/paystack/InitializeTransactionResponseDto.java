package com.decagon.chompapp.dtos.paystack;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InitializeTransactionResponseDto {
    private String status;
    private String message;
    private Data data;
}
