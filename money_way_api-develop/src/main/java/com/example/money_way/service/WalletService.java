package com.example.money_way.service;

import com.example.money_way.dto.request.AccountValidationDto;
import com.example.money_way.dto.request.CreateTransactionPinDto;
import com.example.money_way.dto.request.CreateWalletRequest;
import com.example.money_way.dto.response.ApiResponse;
import com.example.money_way.dto.webhook.VerifyTransaction;
import com.example.money_way.dto.response.ViewWalletResponseDto;
import com.example.money_way.dto.webhook.WebHookResponse;
import org.springframework.http.ResponseEntity;

public interface WalletService {

    ApiResponse<ViewWalletResponseDto> viewBalance();
    ApiResponse createWallet(CreateWalletRequest request);

    ApiResponse<VerifyTransaction> verifyPayment(String transactionId);

    ResponseEntity<String> processWebHookEvent(WebHookResponse<VerifyTransaction> webHookResponse);

    ApiResponse<AccountValidationDto> validateAccount(AccountValidationDto validate);
    ResponseEntity<ApiResponse>  updateWalletPin(CreateTransactionPinDto transactionPin);

}
