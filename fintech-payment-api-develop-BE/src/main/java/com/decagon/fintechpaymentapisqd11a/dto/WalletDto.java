package com.decagon.fintechpaymentapisqd11a.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WalletDto {

    private String firstName;
    private String lastName;
    private Double balance;
    private String acctNumber;
    private String bankName = "Wema Bank";
}
