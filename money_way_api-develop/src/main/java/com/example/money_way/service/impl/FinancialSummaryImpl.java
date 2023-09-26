package com.example.money_way.service.impl;

import com.example.money_way.dto.request.TransactionExpenseDto;
import com.example.money_way.repository.TransactionRepository;
import com.example.money_way.service.FinancialSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
@Service
@RequiredArgsConstructor
public class FinancialSummaryImpl implements FinancialSummary {

    private final TransactionRepository transactionRepository;
    @Override
    public TransactionExpenseDto getTotalExpenseTransaction() {

        BigDecimal total = transactionRepository.findTotalExpenseTransaction();
        return TransactionExpenseDto
                .builder()
                .total(total)
                .build();
    }

}
