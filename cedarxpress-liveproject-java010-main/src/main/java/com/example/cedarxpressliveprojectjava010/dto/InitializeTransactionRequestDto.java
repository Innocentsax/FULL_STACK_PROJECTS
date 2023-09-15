package com.example.cedarxpressliveprojectjava010.dto;

import com.example.cedarxpressliveprojectjava010.enums.Channels;
import com.example.cedarxpressliveprojectjava010.enums.PaystackBearer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InitializeTransactionRequestDto {

    private String amount;
    private String email;
    private String reference;
    private String callback_url;
    private Integer invoice_limit;
    private Channels[] channels;
    private String subAccount;
    private Integer transaction_charge;
    private PaystackBearer paystackBearer = PaystackBearer.ACCOUNT;
}