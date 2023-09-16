package com.decagon.chompapp.services;

import com.decagon.chompapp.dtos.WalletTransactionRequest;
import org.springframework.http.ResponseEntity;

public interface WalletService {
    ResponseEntity<String> fundWalletAccount(WalletTransactionRequest walletTransactionRequest);
    ResponseEntity<String> withdrawFromWallet(WalletTransactionRequest walletTransactionRequest);
    ResponseEntity<String> processPayment(WalletTransactionRequest walletTransactionRequest);
    ResponseEntity<String> checkWalletBalance();
}
