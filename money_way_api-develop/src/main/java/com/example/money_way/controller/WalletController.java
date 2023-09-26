package com.example.money_way.controller;

import com.example.money_way.dto.request.AccountValidationDto;
import com.example.money_way.dto.request.CreateTransactionPinDto;
import com.example.money_way.dto.response.ApiResponse;
import com.example.money_way.dto.webhook.VerifyTransaction;
import com.example.money_way.dto.response.ViewWalletResponseDto;
import com.example.money_way.dto.webhook.WebHookResponse;
import com.example.money_way.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/wallet")
public class WalletController {

    private final WalletService walletService;

    @GetMapping("/view_balance")
    public ResponseEntity<ApiResponse<ViewWalletResponseDto>> viewBalance(){
        return ResponseEntity.ok(walletService.viewBalance());
    }

    @PostMapping("/transaction-webhook")
    public ResponseEntity<String> processWebhookEvent(@RequestBody WebHookResponse<VerifyTransaction> webHookResponse) {
        return walletService.processWebHookEvent(webHookResponse);
    }
    @PostMapping("/validate-account")
    public ResponseEntity<ApiResponse> validateAccount(@RequestBody AccountValidationDto accountValidationDto) {
        return ResponseEntity.ok().body(walletService.validateAccount(accountValidationDto));
    }
    @PatchMapping("/updateWalletPin")
    public ResponseEntity<ApiResponse> updateWalletPin(@Valid @RequestBody CreateTransactionPinDto transactionPinDto){
        return walletService.updateWalletPin(transactionPinDto);
    }
}
