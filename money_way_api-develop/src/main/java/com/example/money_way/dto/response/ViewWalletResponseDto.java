package com.example.money_way.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewWalletResponseDto {

    private Long walletId;
    private BigDecimal balance;
    private String bankName = "Wema Bank";
    private String accountNumber;
}
