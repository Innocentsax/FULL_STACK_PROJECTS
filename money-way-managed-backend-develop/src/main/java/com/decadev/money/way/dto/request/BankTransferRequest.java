package com.decadev.money.way.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BankTransferRequest {

    private String account_number;

    private String account_bank;

    private int amount;

    private String narration;

     private String currency;

     private String reference;

     private String debit_currency;

     private String callback_url;
}
