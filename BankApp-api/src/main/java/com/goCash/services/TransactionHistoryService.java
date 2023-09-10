package com.goCash.services;

import com.goCash.entities.Transaction;
import org.springframework.data.domain.Page;

public interface TransactionHistoryService {
    Page<Transaction> generateTransactionHistory(int page, int pageSize);
}
