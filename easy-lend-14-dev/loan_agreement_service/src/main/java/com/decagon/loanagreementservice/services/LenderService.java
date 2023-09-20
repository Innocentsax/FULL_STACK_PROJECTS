package com.decagon.loanagreementservice.services;

import com.decagon.loanagreementservice.dtos.request.PaymentChoice;
import com.decagon.loanagreementservice.dtos.response.TransactionResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface LenderService {

    TransactionResponse selectLoanRequest(String loanId, HttpServletRequest request, String paymentChoice);
}
