package com.example.money_way.controller;

import com.example.money_way.dto.request.LocalTransferDto;
import com.example.money_way.dto.request.TransferToBankDto;
import com.example.money_way.dto.response.ApiResponse;
import com.example.money_way.dto.response.TransferToBankResponse;
import com.example.money_way.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import javax.validation.Valid;


import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transfers")
public class TransferController {
    private final TransferService transferService;

    @GetMapping("/fee")
    public ResponseEntity<ApiResponse> getTransferFee(@RequestParam("amount") BigDecimal amount) {
        return ResponseEntity.ok(transferService.getTransferFee(amount));
    }

    @PostMapping("/bank")
    public ResponseEntity<ApiResponse> transferToBank(@RequestBody TransferToBankDto transferToBankDto) {
        return ResponseEntity.ok(transferService.transferToBank(transferToBankDto));
    }

    @PostMapping("/bank/response")
    public ResponseEntity<String> updateTransferToBankResponse(
            @RequestBody TransferToBankResponse transferToBankResponse) {
        transferService.updateTransferToBankResponse(transferToBankResponse);
        return ResponseEntity.ok("Transfer Response Updated");
    }

    @PostMapping("/local-transfer")
    public ApiResponse localTransfer(@Valid @RequestBody LocalTransferDto localTransfer)  {
        return transferService.localTransfer(localTransfer);
    }

}
