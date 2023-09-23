package com.example.food.services;

import com.example.food.dto.WithDrawalDto;
import com.example.food.pojos.WalletResponse;
import com.example.food.services.paystack.payStackPojos.Bank;
import org.springframework.http.ResponseEntity;
import java.math.BigDecimal;
import java.util.List;

public interface WalletService {
    WalletResponse getWalletBalance();
    ResponseEntity<String> walletWithdrawal(WithDrawalDto withDrawalDto);
    ResponseEntity<String> fundWallet(BigDecimal amount, String transactionType);
    ResponseEntity<String> verifyPayment(String reference,String transactionType);
    ResponseEntity<List<Bank>> getAllBanks();
}
