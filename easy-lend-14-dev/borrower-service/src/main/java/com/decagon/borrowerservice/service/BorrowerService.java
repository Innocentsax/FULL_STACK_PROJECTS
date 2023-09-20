package com.decagon.borrowerservice.service;


import com.decagon.borrowerservice.dto.LoanRequestDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface BorrowerService {

//   Loan applyLoan(Loan loan);

    LoanRequestDto loanRequest(LoanRequestDto loanRequestDto, HttpServletRequest request);

    LoanRequestDto getLoan(Long id);

    List<LoanRequestDto> getAllLoans();

}