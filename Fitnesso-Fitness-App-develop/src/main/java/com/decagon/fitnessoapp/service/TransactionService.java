package com.decagon.fitnessoapp.service;

import com.decagon.fitnessoapp.dto.transactionDto.TransactionRequestDTO;
import com.decagon.fitnessoapp.dto.transactionDto.TransactionResponseDTO;
import com.decagon.fitnessoapp.dto.transactionDto.response.PaymentResponse;
import com.decagon.fitnessoapp.dto.transactionDto.response.TransVerificationResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.math.BigDecimal;

public interface TransactionService {
    TransactionResponseDTO initializeTransaction(String email, BigDecimal totalPrice,
                                                 String referenceNumber, Long cardNumber);

    PaymentResponse confirmation(String reference);
}
