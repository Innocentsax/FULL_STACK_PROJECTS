package com.decagon.loanagreementservice.services;

import com.decagon.loanagreementservice.dtos.response.LoanAgreementDto;
import com.decagon.loanagreementservice.models.LoanOffer;
import jakarta.servlet.http.HttpServletRequest;

public interface BorrowerService {
    LoanAgreementDto selectLoanOffer(String loanId, HttpServletRequest request);
    LoanOffer getLoanOffer(String offerId);
}
