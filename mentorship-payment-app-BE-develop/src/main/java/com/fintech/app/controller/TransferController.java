package com.fintech.app.controller;

import com.fintech.app.model.FlwBank;
import com.fintech.app.model.Transfer;
import com.fintech.app.request.FlwAccountRequest;
import com.fintech.app.request.LocalTransferRequest;
import com.fintech.app.request.TransferRequest;
import com.fintech.app.request.VerifyTransferRequest;
import com.fintech.app.response.BaseResponse;
import com.fintech.app.response.FlwAccountResponse;
import com.fintech.app.response.OtherBankTransferResponse;
import com.fintech.app.response.VerifyTransferResponse;
import com.fintech.app.service.LocalTransferService;
import com.fintech.app.service.OtherBankTransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/transfers")
@RequiredArgsConstructor
public class TransferController {

    private final OtherBankTransferService otherBankTransferService;

    private final LocalTransferService localTransferService;

    @PostMapping("/local")
    public BaseResponse<Transfer> makeLocalTransfer(@RequestBody LocalTransferRequest localTransferRequest){
        try {
            return localTransferService.makeLocalTransfer(localTransferRequest);
        }catch (Exception e) {
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
    }

    @GetMapping("/resolveLocalAccount/{accountNumber}")
    public BaseResponse<String> resolveLocalAccount(@PathVariable String accountNumber){
        try{
            return localTransferService.resolveLocalAccount(accountNumber);
        }catch (Exception e) {
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
    }

    @GetMapping("/banks")
    public List<FlwBank> getBanks(){
         return otherBankTransferService.getBanks();
    }

    @PostMapping("/resolveOtherBank")
    public BaseResponse<FlwAccountResponse> resolveOtherAccount(@RequestBody FlwAccountRequest flwAccountRequest){
        try{
            return otherBankTransferService.resolveAccount(flwAccountRequest);
        }catch (Exception e) {
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
    }

    @PostMapping("/otherBank")
    public BaseResponse<OtherBankTransferResponse> processTransfer(@Valid @RequestBody TransferRequest transferRequest){
        try {
            return otherBankTransferService.initiateOtherBankTransfer(transferRequest);
        }catch (Exception e) {
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
    }

    @PostMapping("/verify")
    public BaseResponse<VerifyTransferResponse> verifyTransactions(@RequestBody VerifyTransferRequest verifyTransferRequest){
        return otherBankTransferService.verifyTransaction(verifyTransferRequest);
    }

}
