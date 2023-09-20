//package com.example.transactionservice.service.serviceImpl;
//
//import com.example.transactionservice.dto.requests.InitializePaymentRequest;
//import com.example.transactionservice.dto.requests.LoanTransactionRequest;
//import com.example.transactionservice.dto.response.LoanTransactionResponse;
//import com.example.transactionservice.enums.PaymentChoice;
//import com.example.transactionservice.repositories.TransactionRepository;
//import com.example.transactionservice.service.PaymentService;
//import com.example.transactionservice.service.TransactionService;
//import io.micrometer.common.util.StringUtils;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//public class TransactionServiceImpl implements TransactionService {
//    private final TransactionRepository transactionRepository;
//    private final PaymentProcessor paymentProcessor;
//
//    @Override
//    public LoanTransactionResponse initializePay(LoanTransactionRequest request) {
//        if(StringUtils.isBlank(request.getLenderId()) && StringUtils.isBlank(request.getBorrowerId())){
//
//        }
//        String reference = UUID.randomUUID().toString().replace("-", "");
//        InitializePaymentRequest paymentRequest = new InitializePaymentRequest(
//                request.getEmail(), "NGN", String.valueOf(request.getAmount()), reference
//        );
//        PaymentService paymentService=paymentProcessor.getProcessor(PaymentChoice.PAYSTACK);
//        return paymentService.makePayment(paymentRequest);
//    }
//}
//
