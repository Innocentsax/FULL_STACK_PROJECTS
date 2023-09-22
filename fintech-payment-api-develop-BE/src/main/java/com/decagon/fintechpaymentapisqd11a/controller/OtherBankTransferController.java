package com.decagon.fintechpaymentapisqd11a.controller;

import com.decagon.fintechpaymentapisqd11a.models.FlwBank;
import com.decagon.fintechpaymentapisqd11a.request.ExternalBankTransferRequest;
import com.decagon.fintechpaymentapisqd11a.request.FlwResolveAccountRequest;
import com.decagon.fintechpaymentapisqd11a.response.FlwOtherBankTransferResponse;
import com.decagon.fintechpaymentapisqd11a.response.FlwResolveAccountDetails;
import com.decagon.fintechpaymentapisqd11a.services.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transfer")
@RequiredArgsConstructor
public class OtherBankTransferController {

    private final TransferService transferService;

    @GetMapping("/banks")
    public List<FlwBank> getBanks(){

        return transferService.getAllBanks();
    }

    @PostMapping("/otherbank-account-query")
    public ResponseEntity<FlwResolveAccountDetails> resolveAccountDetails(@RequestBody FlwResolveAccountRequest accountRequest){
        return new ResponseEntity<>(transferService.resolveAccount(accountRequest), HttpStatus.OK);
    }

    @PostMapping ("/other-bank")
    public ResponseEntity<FlwOtherBankTransferResponse> processTransfer(@RequestBody ExternalBankTransferRequest transferRequest){
        return new ResponseEntity<>(transferService.initiateOtherBankTransfer(transferRequest), HttpStatus.OK);
    }
}
