package com.example.transactionservice.service.serviceImpl;

import com.example.transactionservice.dto.requests.InitializePaymentRequest;
import com.example.transactionservice.dto.requests.LoanTransactionRequest;
import com.example.transactionservice.dto.response.LoanTransactionResponse;
import com.example.transactionservice.entities.Transactions;
import com.example.transactionservice.enums.PaymentChoice;
import com.example.transactionservice.repositories.TransactionRepository;
import com.example.transactionservice.securityConfig.JWTUtils;
import com.example.transactionservice.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

import static com.example.transactionservice.enums.PaymentChoice.MOCKED;

@Service
@RequiredArgsConstructor
public class MockedPaymentServiceImpl implements PaymentService {

    private final TransactionRepository transactionRepository;
    private final JWTUtils jwtUtils;

    @Override
    public LoanTransactionResponse makePayment(LoanTransactionRequest request, HttpServletRequest servletRequest) {
        String authHeader = servletRequest.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("permission denied");
        }

        String token = authHeader.substring(7);
        if (!jwtUtils.getUserTypeFromToken(token).equalsIgnoreCase("LENDER")) {
            throw new RuntimeException("permission denied");
        }

        Transactions transactions = new Transactions();
        String transactionId = UUID.randomUUID().toString().replace("-","").substring(9);
        if(Objects.isNull(request)){
            //TODO Controller Adviser
            throw new RuntimeException();

        }
        transactions.setLoanId(request.getLoanId());
        transactions.setAmount(request.getAmount());
        transactions.setBorrowId(request.getBorrowerId());
        transactions.setPaymentChoice(MOCKED);
        transactions.setTransactionId(transactionId);
        LoanTransactionResponse paymentResponse=new LoanTransactionResponse();
        Transactions savedTransaction = transactionRepository.save(transactions);
        paymentResponse.setStatus(true);
        paymentResponse.setMessage("successful");
        paymentResponse.setData(savedTransaction);
        return paymentResponse;
    }


}
