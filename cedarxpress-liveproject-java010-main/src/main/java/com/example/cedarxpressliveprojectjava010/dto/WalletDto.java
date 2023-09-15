package com.example.cedarxpressliveprojectjava010.dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class WalletDto {
    private BigDecimal currentBalance;
}
