package com.example.cedarxpressliveprojectjava010.service;

import com.example.cedarxpressliveprojectjava010.dto.Amount;
import com.example.cedarxpressliveprojectjava010.dto.InitializeTransactionResponseDto;
import org.springframework.http.ResponseEntity;
import java.io.IOException;

public interface InitializeTransactionService {
    InitializeTransactionResponseDto initializeTransactionResponseDto(Amount amount);
    ResponseEntity<String> verifyPayment(String reference) throws IOException;
}