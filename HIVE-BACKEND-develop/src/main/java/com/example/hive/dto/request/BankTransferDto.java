package com.example.hive.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Data
@RequiredArgsConstructor
public class BankTransferDto {

    @Positive
    private  BigDecimal amount;
    @NotEmpty
    private  String currencyCode;
    @NotEmpty
    private  String narration;
    @NotEmpty
    private  String beneficiaryAccountNumber;
    String beneficiaryAccountName;
    @NotEmpty
    private  String beneficiaryBankCode;
    String transactionReference;
    int maxRetryAttempt = 0;
    String callBackUrl;

}
