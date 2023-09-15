package com.example.cedarxpressliveprojectjava010.dto.request;

import lombok.*;
import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalletWithdrawalRequest {

    private String email;
    private BigDecimal amount;
}
