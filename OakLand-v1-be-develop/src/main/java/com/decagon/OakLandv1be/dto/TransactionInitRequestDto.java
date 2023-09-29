package com.decagon.OakLandv1be.dto;


import com.decagon.OakLandv1be.enums.Channels;
import com.decagon.OakLandv1be.enums.PaystackBearer;
import lombok.*;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionInitRequestDto {
    private String amount;
    private String reference;
    private String email;
    private PaystackBearer paystackBearer = PaystackBearer.ACCOUNT;
    private String callback_url;
    private Integer invoice_limit;
    private Channels[] channels;

}
