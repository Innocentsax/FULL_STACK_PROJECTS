package com.example.food.services.paystack.payStackPojos;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class WithdrawalDto {
    private String account_number;
    private String account_name;
    private int bank_id;
    private String bankCode;
    private String recipient_code;
    private String reference;
}
