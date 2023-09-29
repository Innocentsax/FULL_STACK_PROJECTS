package com.decagon.OakLandv1be.dto;


import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FundWalletResponseDto {

    private String fullName;
    private BigDecimal depositAmount;
    private BigDecimal newBalance;

}
