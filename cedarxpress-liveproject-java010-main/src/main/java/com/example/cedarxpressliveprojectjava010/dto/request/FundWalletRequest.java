package com.example.cedarxpressliveprojectjava010.dto.request;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FundWalletRequest {

    private String email;
    private BigDecimal amount;
}
