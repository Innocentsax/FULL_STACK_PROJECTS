package com.decagon.chompapp.dtos.paystack;
import com.decagon.chompapp.enums.paystack.PaystackBearer;
import com.decagon.chompapp.enums.paystack.PaystackChannels;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
public class InitializeTransactionRequestDto {
    private String amount;
    private String email;
    private String reference;
    private String callback_url;
    private Integer invoice_limit;
    private PaystackChannels[] channels;
    private String subaccount;
    private Integer transaction_charge;

    @Enumerated(EnumType.STRING)
    private PaystackBearer paystackBearer = PaystackBearer.ACCOUNT;
}
