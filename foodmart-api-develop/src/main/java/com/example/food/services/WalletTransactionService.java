package com.example.food.services;

import com.example.food.pojos.WalletTransactionResponse;
import org.springframework.stereotype.Service;

@Service
public interface WalletTransactionService {
   WalletTransactionResponse  viewWalletTransaction();
}
