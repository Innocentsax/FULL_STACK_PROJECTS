package com.goCash.controller;

import com.goCash.dto.request.AccountVerificationRequest;
import com.goCash.dto.request.LocalTransferRequest;
import com.goCash.dto.response.WalletResponseDto;
import com.goCash.services.WalletServices;
import com.goCash.utils.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@Slf4j
@RestController
@RequestMapping(path = "/wallet")
@AllArgsConstructor
public class WalletController {

    private final WalletServices walletServices;
    @GetMapping("/fetch-wallet-details")
    public ResponseEntity<ApiResponse<WalletResponseDto>> fundWallet() {
        ApiResponse<WalletResponseDto> response = walletServices.fundWallet();
        log.info("User is trying to fetch details");
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @GetMapping("/fetch-balance")
    public ResponseEntity<ApiResponse<Double>> balanceEnquiry() {
        ApiResponse<Double> response = walletServices.balanceEnquiry();
        log.info("User is trying to fetch balance");
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @PostMapping("/local-transfer")
    public ResponseEntity<ApiResponse> walletToWallet(@RequestBody LocalTransferRequest localTransferRequest) {
        ApiResponse apiResponse = walletServices.walletToWalletTransfer(localTransferRequest);
        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
    }

    @PostMapping("/query-local-account")
    public ResponseEntity<ApiResponse> verifyLocalAccount(@RequestBody AccountVerificationRequest request) {
        ApiResponse apiResponse = walletServices.verifyLocalAccount(request);
        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
    }
}
