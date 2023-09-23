package com.example.hive.service;

import com.example.hive.dto.request.FundWalletRequest;
import com.example.hive.dto.response.PayStackResponse;

import com.example.hive.dto.response.VerifyTransactionResponse;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

public interface PaymentService {



    PayStackResponse initiatePaymentAndSaveToPaymentLog(FundWalletRequest taskerPaymentRequest, Principal principal) throws Exception;

    VerifyTransactionResponse verifyAndCompletePayment(String reference , Principal principal) throws Exception;


    VerifyTransactionResponse verifyAndCompletePayment(String reference) throws Exception;
}
