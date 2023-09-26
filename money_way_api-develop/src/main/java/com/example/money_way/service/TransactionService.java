package com.example.money_way.service;

import com.example.money_way.dto.request.TransactionLogRequest;
import com.example.money_way.dto.response.ApiResponse;
import com.example.money_way.dto.response.FinancialSummaryResponseDto;
import com.example.money_way.dto.response.TransactionLogResponse;

import java.util.List;

public interface TransactionService {
    ApiResponse<List<TransactionLogResponse>> viewTransactionLog(TransactionLogRequest request);

    List<FinancialSummaryResponseDto> getTransactionGraphByMonth();
}
