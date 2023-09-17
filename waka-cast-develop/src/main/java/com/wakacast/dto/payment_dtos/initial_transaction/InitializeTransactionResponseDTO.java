package com.wakacast.dto.payment_dtos.initial_transaction;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InitializeTransactionResponseDTO {
    private String status;
    private String message;
    private Data data;
}
