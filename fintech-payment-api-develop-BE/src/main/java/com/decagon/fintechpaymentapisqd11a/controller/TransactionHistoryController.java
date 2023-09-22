package com.decagon.fintechpaymentapisqd11a.controller;

import com.decagon.fintechpaymentapisqd11a.dto.TransactionHistoryDto;
import com.decagon.fintechpaymentapisqd11a.pagination_criteria.TransactionHistoryPages;
import com.decagon.fintechpaymentapisqd11a.services.TransactionHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class TransactionHistoryController {
    private final TransactionHistory transactionHistory;
    @GetMapping("/transactionHistory")
    public ResponseEntity<PageImpl<TransactionHistoryDto>> viewTransactionHistory(TransactionHistoryPages transactionHistoryPages) {
        return ResponseEntity.ok(transactionHistory.allTransaction(transactionHistoryPages));
    }
    }
