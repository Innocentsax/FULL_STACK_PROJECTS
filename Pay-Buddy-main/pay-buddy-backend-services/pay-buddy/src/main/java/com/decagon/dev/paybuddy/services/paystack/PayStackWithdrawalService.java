package com.decagon.dev.paybuddy.services.paystack;

import com.decagon.dev.paybuddy.dtos.requests.WithdrawalDto;
import com.decagon.dev.paybuddy.services.paystack.payStackPojos.Bank;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

public interface PayStackWithdrawalService {
    ResponseEntity<List<Bank>> getAllBanks();
    ResponseEntity<?> withDrawFromWallet(WithdrawalDto withdrawalDto);
    ResponseEntity<String> verifyAccountNumber(String account_number, String bank_code);
}
