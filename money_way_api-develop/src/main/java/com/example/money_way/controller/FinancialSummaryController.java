package com.example.money_way.controller;

import com.example.money_way.dto.request.TransactionExpenseDto;
import com.example.money_way.dto.response.ApiResponse;
import com.example.money_way.service.FinancialSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/financial")
public class FinancialSummaryController {

    private final FinancialSummary financialSummary;

    @GetMapping("/summary")
    public ResponseEntity<TransactionExpenseDto> financialSummary(){
        return  ResponseEntity.ok(financialSummary.getTotalExpenseTransaction());
    }

}
