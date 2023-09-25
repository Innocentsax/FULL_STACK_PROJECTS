package com.decadev.money.way.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FundWalletRequest {

    private String tx_ref;
    private String amount;
    private String account_number;
    private String account_bank;
    private String currency;
    private String email;
    private String phone_number;
    private String fullname;
    private String pin;
    private String description;

}
