package com.example.money_way.service;

import com.example.money_way.dto.request.LocalTransferDto;
import com.example.money_way.dto.request.TransferToBankDto;
import com.example.money_way.dto.response.ApiResponse;
import com.example.money_way.dto.response.TransferToBankResponse;

import java.math.BigDecimal;

public interface TransferService {
    ApiResponse getTransferFee(BigDecimal amount);
    ApiResponse transferToBank(TransferToBankDto transferToBankDto);
    void updateTransferToBankResponse(TransferToBankResponse transferToBankResponse);
    ApiResponse localTransfer (LocalTransferDto localTransfer);

}
