package com.example.hive.service;

import com.example.hive.dto.request.BankTransferDto;
import com.example.hive.dto.request.PayStackPaymentRequest;
import com.example.hive.dto.response.ListBanksResponse;
import com.example.hive.dto.response.PayStackResponse;
import com.example.hive.dto.response.TransactionResponse;
import com.example.hive.dto.response.VerifyTransactionResponse;
import com.example.hive.entity.User;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

public interface PayStackService {

    PayStackResponse initTransaction(Principal principal, PayStackPaymentRequest request) throws Exception;

    VerifyTransactionResponse verifyPayment(String reference) throws IOException;

    List<ListBanksResponse> fetchBanks(String provider);

    Mono<TransactionResponse> transferFunds(BankTransferDto dto, String provider, User user) throws InterruptedException;


}
