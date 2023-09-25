package com.decadev.money.way.controller;

import com.decadev.money.way.dto.request.AirtimeRequest;
import com.decadev.money.way.dto.request.FundWalletRequest;
import com.decadev.money.way.dto.request.CashTransferRequest;
import com.decadev.money.way.dto.request.TransactionHistoryRequest;
import com.decadev.money.way.dto.response.BeneficiaryResponse;
import com.decadev.money.way.dto.response.TransactionHistoryResponse;
import com.decadev.money.way.dto.response.VirtualAccountCreationResponse;
import com.decadev.money.way.exception.UserNotFoundException;
import com.decadev.money.way.flutterAPI.AirtimeClient;
import com.decadev.money.way.flutterAPI.FlutterService;
import com.decadev.money.way.model.Beneficiary;
import com.decadev.money.way.service.TransactionService;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000/")
@RequestMapping("/api/v1/transaction")
public class TransactionController {
    private final TransactionService transactionService;
    private final FlutterService flutterService;
    private final AirtimeClient airtimeClient;

    @GetMapping("/transaction-history")
    public ResponseEntity<List<TransactionHistoryResponse>> transactionHistory(
            @RequestBody TransactionHistoryRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.transactionHistory(request).getBody());
    }

    @GetMapping("/verify-recipient")
    public ResponseEntity<String> verifyBeneficiary(@RequestParam("account-number") String request) throws Exception {
        return ResponseEntity.ok().body(transactionService.verifyRecipientDetails(request));
    }

    @PostMapping("/local-transfer")
    public ResponseEntity<String> interWalletTransfer(@RequestBody @Valid CashTransferRequest request) throws UserNotFoundException {
        return ResponseEntity.ok().body(transactionService.transferToWallet(request));
    }

    @GetMapping("/beneficiaries")
    public ResponseEntity<List<BeneficiaryResponse>> getSavedBeneficiaries(@RequestParam("transaction-type") String transactionType) throws UserNotFoundException {
       return ResponseEntity.ok().body(transactionService.getBeneficiaries(transactionType));
    }

    @PostMapping("/fund-wallet")
    public ResponseEntity<VirtualAccountCreationResponse> fundWallet(@RequestBody FundWalletRequest request) throws JsonProcessingException, UserNotFoundException {
        return ResponseEntity.ok().body(flutterService.bankAccountCharge(request));
    }

    @PostMapping("/bank-transfer")
    public ResponseEntity<?> transferToBank(@RequestBody CashTransferRequest request) throws JsonProcessingException {
        return ResponseEntity.ok().body(flutterService.transferToOtherBank(request));
    }

    @PostMapping("/airtime")
    public ResponseEntity<?> buyAirtime(@RequestBody AirtimeRequest request) throws JsonProcessingException {
        return ResponseEntity.ok().body(airtimeClient.payAirtimeBill(request));
    }

}
