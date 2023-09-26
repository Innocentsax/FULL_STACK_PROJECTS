package com.example.money_way.controller;

import com.example.money_way.dto.request.TransactionLogRequest;
import com.example.money_way.dto.response.ApiResponse;
import com.example.money_way.dto.response.FinancialSummaryResponseDto;
import com.example.money_way.dto.response.TransactionLogResponse;
import com.example.money_way.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/")
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping("transactions")
    public ResponseEntity<ApiResponse<List<TransactionLogResponse>>> viewTransactions(
            @RequestBody TransactionLogRequest request){
        return ResponseEntity.ok(transactionService.viewTransactionLog(request));
    }

    @GetMapping("transactions/by-month")
    public ResponseEntity<List<FinancialSummaryResponseDto>> getTransactionsByMonth() {
        return ResponseEntity.ok(transactionService.getTransactionGraphByMonth());
    }
}
