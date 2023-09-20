package com.decagon.loanagreementservice.services.serviceImpl;

import com.decagon.loanagreementservice.dtos.request.TransactionRequest;
import com.decagon.loanagreementservice.dtos.response.TransactionResponse;
import com.decagon.loanagreementservice.exceptions.AgreementNotFoundException;
import com.decagon.loanagreementservice.exceptions.UserNotAuthorizedException;
import com.decagon.loanagreementservice.models.LoanAgreement;
import com.decagon.loanagreementservice.models.Status;
import com.decagon.loanagreementservice.repository.AgreementRepository;
import com.decagon.loanagreementservice.security_config.JwtUtils;
import com.decagon.loanagreementservice.services.LenderService;
import com.decagon.loanagreementservice.services.TransactionClient;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
@RequiredArgsConstructor
public class LenderServiceImpl implements LenderService {
    private final AgreementRepository agreementRepository;

    private final JwtUtils jwtUtils;
    private final TransactionClient transactionClient;

    @Override
    public TransactionResponse selectLoanRequest(String loanId, HttpServletRequest request, String paymentChoice) {
        String auth = request.getHeader("Authorization");
        String token = auth.substring(7);
        if (!jwtUtils.getUserTypeFromToken(token).equalsIgnoreCase("LENDER")) {
            throw new UserNotAuthorizedException("permission denied");
        }
        LoanAgreement loanAgreement = agreementRepository.findByLoanId(loanId).get();
        if (Objects.isNull(loanAgreement)) {
            throw new AgreementNotFoundException("Loan Agreement not found");
        }
        loanAgreement.setStatus(Status.APPROVED);
        TransactionRequest transactionRequest = new TransactionRequest(
                loanAgreement.getLoanAmount(),
                loanAgreement.getBorrowerId(),
                loanAgreement.getLoanId(),
                paymentChoice);
        TransactionResponse transactionResponse = getTransactionRequest(transactionRequest);
        if (Objects.isNull(transactionResponse)) {
            return new TransactionResponse(true, "transaction successful");
        }

        throw new AgreementNotFoundException("Agreement not found");
    }


    public TransactionResponse getTransactionRequest(TransactionRequest transactionRequest) {
        // todo Use ur FeignClient to call the loanOffer service
        ResponseEntity<TransactionResponse> response = transactionClient.getTransactionRequest(transactionRequest);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            // Handle error case
            throw new RuntimeException("FAiled to communicate with transaction service.");
        }
    }
}