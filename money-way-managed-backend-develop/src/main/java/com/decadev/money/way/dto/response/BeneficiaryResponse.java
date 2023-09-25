package com.decadev.money.way.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BeneficiaryResponse {
    private String name;

    private String accountNumber;

    private String phoneNumber;

    private String accountBank;

    private String transactionType;
}
