package com.decagon.fitnessoapp.dto.transactionDto;

import lombok.Data;

@Data
public class PaymentData {
    private String authorization_url;
    private String access_code;
    private String reference;
}
