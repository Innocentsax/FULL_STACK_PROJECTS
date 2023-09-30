package com.decagon.dev.paybuddy.services.paystack.payStackPojos;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankDetailsResponse {
    private boolean status;
    private String message;
    private List<BankResponseDto> data;
}
