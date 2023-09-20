package com.decagon.loanagreementservice.controllers;


import com.decagon.loanagreementservice.services.BorrowerService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/borrower")
public class BorrowerController {
    private final BorrowerService service;

    @GetMapping("/accept_offer/{offerId}")
    public ResponseEntity<String> selectLoanOffer(@PathVariable("offerId") String loanId, HttpServletRequest request) {
        // code to select the loan based on the loanId

        service.selectLoanOffer(loanId, request);
        return ResponseEntity.ok("Loan selected successfully");
    }


}
