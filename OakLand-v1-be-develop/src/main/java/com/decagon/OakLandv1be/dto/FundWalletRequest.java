package com.decagon.OakLandv1be.dto;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FundWalletRequest {

    private BigDecimal amount;
    private String email;
}
