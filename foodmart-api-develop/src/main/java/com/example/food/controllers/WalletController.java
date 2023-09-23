package com.example.food.controllers;

import com.example.food.dto.WithDrawalDto;
import com.example.food.pojos.WalletResponse;
import com.example.food.services.WalletService;
import com.example.food.services.paystack.PayStackWithdrawalService;
import com.example.food.services.paystack.payStackPojos.Bank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080"})
@RequestMapping("/wallet")
public class WalletController {
    private final WalletService walletService;

    @GetMapping("/balance")
    public ResponseEntity<WalletResponse> getBalance() {
        WalletResponse response = walletService.getWalletBalance();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/getBankDetails")
    public ResponseEntity<List<Bank>> fetchBankDetails(){
        return walletService.getAllBanks();
    }
    @PostMapping("/withdrawal")
    public ResponseEntity<String> walletWithdrawal(@RequestBody WithDrawalDto withDrawalDto){
        return walletService.walletWithdrawal(withDrawalDto);
    }
    @PostMapping("/fundWallet")
    public ResponseEntity<String> fundWallet(@RequestParam BigDecimal amount){
        return walletService.fundWallet(amount, "fundwallet");
    }
    @PostMapping("/makePayment")
    public ResponseEntity<String> makePayment(@RequestParam BigDecimal amount){
        return walletService.fundWallet(amount,"makepayment");
    }
    @GetMapping("/verifyPayment/{reference}/{paymentMethod}")
    public ResponseEntity<String> verifyPayment(@PathVariable  String reference, @PathVariable  String paymentMethod){
        return walletService.verifyPayment(reference,paymentMethod);
    }
}
