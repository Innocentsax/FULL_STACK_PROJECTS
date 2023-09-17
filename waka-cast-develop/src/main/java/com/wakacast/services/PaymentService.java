package com.wakacast.services;

import com.wakacast.dto.SubscriptionDTO;
import com.wakacast.dto.payment_dtos.initial_transaction.InitializeTransactionResponseDTO;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface PaymentService {
    InitializeTransactionResponseDTO initializeTransaction (
            SubscriptionDTO subscriptionDTO, HttpServletRequest request
    );

    String confirmPayment(String token, String reference) throws IOException;
}
