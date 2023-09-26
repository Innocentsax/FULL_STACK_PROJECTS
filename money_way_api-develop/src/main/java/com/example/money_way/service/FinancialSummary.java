package com.example.money_way.service;

import com.example.money_way.dto.request.TransactionExpenseDto;

public interface FinancialSummary {
    TransactionExpenseDto getTotalExpenseTransaction();
}
