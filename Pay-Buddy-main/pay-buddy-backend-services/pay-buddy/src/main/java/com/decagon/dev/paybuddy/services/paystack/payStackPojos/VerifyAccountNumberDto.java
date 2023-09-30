package com.decagon.dev.paybuddy.services.paystack.payStackPojos;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class VerifyAccountNumberDto {
    private String account_number;
}
