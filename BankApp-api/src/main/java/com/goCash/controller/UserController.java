package com.goCash.controller;

import com.goCash.dto.request.ChangePasswordRequestDto;
import com.goCash.dto.request.TransferRequest;
import com.goCash.dto.request.TransferRequestDto;
import com.goCash.dto.response.TransferResponse;
import com.goCash.entities.Transaction;
import com.goCash.services.FlutterWaveService;
import com.goCash.services.TransactionHistoryService;
import com.goCash.services.implementations.UserServiceImp;
import com.goCash.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserServiceImp appUserService;
    private final FlutterWaveService flutterWaveService;
    private final TransactionHistoryService transactionHistoryService;


    @GetMapping("/user")
    public ResponseEntity<ApiResponse> viewUser() {
        log.info("call the userservice to view user profiles");
        ApiResponse apiResponse = appUserService.getUser();
        return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);
    }


    @PostMapping("/transfer-to-bank")
    public ResponseEntity<ApiResponse<TransferResponse>> transferToBank(@RequestBody TransferRequestDto request) {
        log.info("request for transfer money to bank");
        ApiResponse<TransferResponse> response = flutterWaveService.transferToBank(request);
        return ResponseEntity.status(response.getHttpStatus()).body(response);


    }

    @PostMapping("/change-password")
    public ResponseEntity<ApiResponse<?>>changePassword(
            @RequestBody @Valid ChangePasswordRequestDto requestDto) {
        log.info("Received request to change password via API");

        // Call the changePassword method in the UserService
        ApiResponse<ChangePasswordRequestDto> apiResponse = appUserService.changePassword(requestDto);

        // Return the ApiResponse
        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
    }
    @GetMapping("/generateAllTransactionHistory")
    public ResponseEntity<Page<Transaction>> generateTransactionHistory(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize) {
        Page<Transaction> transactionPage = transactionHistoryService.generateTransactionHistory(page, pageSize);
        return ResponseEntity.ok(transactionPage);
    }
}