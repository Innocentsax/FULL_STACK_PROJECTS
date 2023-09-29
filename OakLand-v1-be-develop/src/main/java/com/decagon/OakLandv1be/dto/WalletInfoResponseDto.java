package com.decagon.OakLandv1be.dto;

import com.decagon.OakLandv1be.entities.Wallet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalletInfoResponseDto {
    private String firstName;
    private String lastName;
    private String email;
    private String walletBalance;
    private String baseCurrency;

}
