package com.decagon.dev.paybuddy.dtos.responses.vtpass.response.data;

import com.decagon.dev.paybuddy.dtos.requests.WalletTransactionDto;
import com.decagon.dev.paybuddy.restartifacts.BaseResponse;

import java.util.List;

public class WalletTransactionResponse extends BaseResponse {
    private List<WalletTransactionDto> walletTransaction;
}
