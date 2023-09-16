package com.decagon.chompapp.services.paystack;

import com.decagon.chompapp.dtos.paystack.InitializeTransactionRequestDto;
import com.decagon.chompapp.dtos.paystack.InitializeTransactionResponseDto;

import java.io.IOException;

public interface PaymentService {
    InitializeTransactionResponseDto initializeTransaction
            (InitializeTransactionRequestDto transactionRequestDto);

    StringBuilder verifyTransaction (String reference) throws IOException;
}
