package com.decadev.money.way.service;

import com.decadev.money.way.dto.request.CashTransferRequest;
import com.decadev.money.way.dto.request.TransactionHistoryRequest;
import com.decadev.money.way.dto.response.BeneficiaryResponse;
import com.decadev.money.way.dto.response.TransactionHistoryResponse;
import com.decadev.money.way.exception.UserNotFoundException;
import com.decadev.money.way.model.Beneficiary;
import com.decadev.money.way.model.Wallet;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TransactionService {

    ResponseEntity<List<TransactionHistoryResponse>> transactionHistory(TransactionHistoryRequest request);

    String verifyRecipientDetails(String request) throws UserNotFoundException;

    String transferToWallet(CashTransferRequest request) throws UserNotFoundException;

    List <BeneficiaryResponse> getBeneficiaries(String transactionType) throws UserNotFoundException;

    String debitFundSenderWallet(String amount, Wallet wallet);

    String creditRecipientWallet(String amount, Wallet wallet);
}
