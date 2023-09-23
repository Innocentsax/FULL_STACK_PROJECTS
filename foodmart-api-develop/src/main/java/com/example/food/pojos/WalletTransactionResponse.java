package com.example.food.pojos;

import com.example.food.dto.WalletTransactionDto;
import com.example.food.restartifacts.BaseResponse;
import lombok.*;

import java.util.List;

@Builder
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WalletTransactionResponse extends BaseResponse{
    private List<WalletTransactionDto> walletTransactions;
}
