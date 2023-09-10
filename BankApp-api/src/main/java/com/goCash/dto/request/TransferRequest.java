package com.goCash.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class TransferRequest {
    @JsonProperty("account_bank")
    private String accountBank;
    @JsonProperty("account_number")
    private String accountNumber;
    @JsonProperty("amount")
    private double amount;
    @JsonProperty("narration")
    private String narration;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("beneficiary_name")
    private String beneficiaryName;
    @JsonProperty("reference")
    private String reference;
    @JsonProperty ("debit_currency")
    private String debitCurrency;
    @JsonProperty("meta")
    private Meta meta;

    public TransferRequest(TransferRequestDto transferRequestDto, Meta meta) {

        this.accountBank = transferRequestDto.getAccountBank();
        this.accountNumber=transferRequestDto.getAccountNumber();
        this.amount = transferRequestDto.getAmount();
        this.narration= transferRequestDto.getNarration();
        this.currency=transferRequestDto.getCurrency();
        this.beneficiaryName=transferRequestDto.getBeneficiaryName();
        this.debitCurrency=transferRequestDto.getDebitCurrency();
        this.meta= meta;
    }
}
