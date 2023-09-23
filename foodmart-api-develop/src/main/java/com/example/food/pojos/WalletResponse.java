package com.example.food.pojos;

import com.example.food.restartifacts.BaseResponse;
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
}
