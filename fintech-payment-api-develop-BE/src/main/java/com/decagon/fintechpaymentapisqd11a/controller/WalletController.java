package com.decagon.fintechpaymentapisqd11a.controller;

import com.decagon.fintechpaymentapisqd11a.dto.WalletDto;
import com.decagon.fintechpaymentapisqd11a.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;
    @RequestMapping ("/viewWallet")
    public ResponseEntity<WalletDto> viewWallet() {
        WalletDto walletDto = walletService.viewWalletDetails();
        return new ResponseEntity<>(walletDto, HttpStatus.OK);
    }

}
