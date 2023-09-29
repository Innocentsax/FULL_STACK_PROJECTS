package com.decagon.OakLandv1be.controllers;


import com.decagon.OakLandv1be.dto.FundWalletRequest;
import com.decagon.OakLandv1be.dto.FundWalletResponseDto;
import com.decagon.OakLandv1be.dto.cartDtos.ProcessPaymentRequest;
import com.decagon.OakLandv1be.services.WalletService;
import com.decagon.OakLandv1be.utils.ApiResponse;
import com.decagon.OakLandv1be.utils.ResponseManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class WalletController {

    private final WalletService walletService;

    @PostMapping("customer/wallet/fund")
    public ApiResponse<Object> fundWallet(@RequestBody FundWalletRequest request){
        return new ApiResponse("Request successful", walletService.fundWallet(request), HttpStatus.OK);
    }


    @GetMapping("customer/wallet/balance")
    public ResponseEntity<ApiResponse<Object>> getBalance(){
        BigDecimal response = walletService.getWalletBalance();
        return new ResponseEntity<>(new ResponseManager().success(response), HttpStatus.OK);
    }

    @GetMapping("customer/wallet/info")
    public ApiResponse<Object> walletInfo(){

        return new ApiResponse<>("Request successful", walletService.viewWalletInfo(), HttpStatus.OK);
    }


    @GetMapping("customer/wallet/transactions")
    public ResponseEntity<Object> fetchAllTrans(@RequestParam(defaultValue = "0") Integer pageNo,
                                                @RequestParam(defaultValue = "10") Integer pageSize,
                                                @RequestParam(defaultValue = "id") String sortBy) {

        return ResponseEntity.ok(walletService.fetchAllTransactions(pageNo, pageSize, sortBy));
    }

    @PutMapping("customer/wallet/process-payment")
    public  ResponseEntity<Boolean> processPayment(@RequestBody ProcessPaymentRequest processPaymentyRequest){
        return ResponseEntity.ok(walletService.processPayment(processPaymentyRequest.getGrandTotal()));

    }
    @GetMapping("/admin/view-all-wallet")
    public ResponseEntity<Page<FundWalletResponseDto>> getCustomerWallet(@RequestParam(defaultValue = "0") Integer pageNo,
                                                                            @RequestParam(defaultValue = "10") Integer pageSize,
                                                                            @RequestParam(defaultValue = "id") String sortBy) {
        return new ResponseEntity<>(walletService.viewCustomerWalletByPagination(pageNo, pageSize, sortBy), HttpStatus.OK);
    }

}
