package com.wakacast.dto.payment_dtos.initial_transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wakacast.enums.PayStackBearer;
import com.wakacast.enums.PaymentChannel;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class InitializeTransactionRequestDTO {
    private String amount;
    private String email;
    private String reference;
    @JsonProperty("callback_url")
    private String callbackUrl;
    @JsonProperty("invoice_limit")
    private Integer invoiceLimit;
    private String plan;
    private PaymentChannel[] paymentChannels;
    @JsonProperty("subaccount")
    private String subAccount;
    @JsonProperty("transaction_charge")
    private Integer transactionCharge;
    private PayStackBearer paystackBearer = PayStackBearer.ACCOUNT;
}
