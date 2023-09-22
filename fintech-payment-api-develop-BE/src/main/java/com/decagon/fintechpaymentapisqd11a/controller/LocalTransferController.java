package com.decagon.fintechpaymentapisqd11a.controller;


import com.decagon.fintechpaymentapisqd11a.dto.LocalBankTransferDto;
import com.decagon.fintechpaymentapisqd11a.services.serviceImpl.LocalTransferServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/transfer")
public class LocalTransferController {
    private final LocalTransferServiceImpl localTransferService;

    @PutMapping("/localTransfer")
    public String localTransfer(@RequestBody LocalBankTransferDto localBankTransferDto){
        return localTransferService.localTransfer(localBankTransferDto);
    }
}
