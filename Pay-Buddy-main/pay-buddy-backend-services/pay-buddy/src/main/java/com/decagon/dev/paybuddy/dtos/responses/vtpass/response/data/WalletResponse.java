package com.decagon.dev.paybuddy.dtos.responses.vtpass.response.data;

import com.decagon.dev.paybuddy.restartifacts.BaseResponse;
import lombok.*;

import java.math.BigDecimal;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class WalletResponse extends BaseResponse {
    private String userName;
    private BigDecimal walletBalance;
    private String accountNumber;
    private boolean isPinUpdated;
}
