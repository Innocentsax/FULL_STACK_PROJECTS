package com.decagon.borrowerservice.service.impl;

import com.decagon.borrowerservice.dto.LoanRequestDto;
import com.decagon.borrowerservice.entities.LoanRequest;
import com.decagon.borrowerservice.exception.BorrowerNotFoundException;
import com.decagon.borrowerservice.exception.handler.UserNotAuthorizedException;
import com.decagon.borrowerservice.repository.BorrowerRepository;
import com.decagon.borrowerservice.service.BorrowerService;
import com.decagon.borrowerservice.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BorrowerServiceImpl implements BorrowerService {

    private final BorrowerRepository borrowerRepository;
    private final ModelMapper modelMapper;
    private final JwtUtils jwtUtils;


    @Override
    public LoanRequestDto loanRequest(LoanRequestDto loanRequestDto, HttpServletRequest request) {
        String auth = request.getHeader("Authorization");
        String token = null;

        if (Objects.isNull(auth )|| !auth.startsWith("Bearer ")){
            throw  new UserNotAuthorizedException("permission denied");

        }

         token = auth.substring(7);

        // Loan application validation

        if(!jwtUtils.getUserTypeFromToken(token).equalsIgnoreCase("BORROWER")){
            throw  new UserNotAuthorizedException("permission denied");
        }

//in same pattern as loanRequest.setLoanAmt(loanRequestDto.getLoanAmt());

        String userId = jwtUtils.getUserIdFromToken(token);



        if (loanRequestDto.getLoanAmt() <= 0) {
            throw new IllegalArgumentException("Loan amount must be greater than zero");
        }
        if (loanRequestDto.getInterestRate() <= 0 || loanRequestDto.getInterestRate() > 100) {

            throw new IllegalArgumentException("Invalid interest rate");}

        if (loanRequestDto.getRepaymentTerm() <= 0) {
               throw new IllegalArgumentException("Repayment term must be greater than zero");

        }

        // Calculating the total repayment amount
        double totalRepayment = loanRequestDto.getLoanAmt() * (1 + loanRequestDto.getInterestRate() / 100);

        LoanRequest loanRequest = new LoanRequest();

        // Set the total repayment amount in the loanDto object
        loanRequest.setTotalRepayment(totalRepayment);
        loanRequest.setUserId(userId);
        loanRequest.setLoanAmt(loanRequestDto.getLoanAmt());
        loanRequest.setPurpose(loanRequestDto.getPurpose());
        loanRequest.setRepaymentTerm(loanRequestDto.getRepaymentTerm());


        // Map LoanDto to Loan entity and save it
//        LoanRequest borrower = modelMapper.map(loanRequestDto, LoanRequest.class);
        LoanRequest savedLoan = borrowerRepository.save(loanRequest);


        // Map the saved Loan entity back to LoanDto and return it
        return new LoanRequestDto(savedLoan);
    }
    @Override
    public LoanRequestDto getLoan(Long id) {
        LoanRequest loanRequestDto  = borrowerRepository.findById(id)
                .orElseThrow(()-> new BorrowerNotFoundException("Borrower Not found with id : " + id));
        return modelMapper.map(loanRequestDto, LoanRequestDto.class);
    }
    @Override
    public List<LoanRequestDto> getAllLoans() {
        List <LoanRequest> borrowers = borrowerRepository.findAll();
        return borrowers.stream().map((borrower) -> modelMapper.map(borrower, LoanRequestDto.class))
                .collect(Collectors.toList());
    }

}

