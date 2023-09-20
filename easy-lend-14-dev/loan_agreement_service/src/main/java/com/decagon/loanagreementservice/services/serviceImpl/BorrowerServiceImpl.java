package com.decagon.loanagreementservice.services.serviceImpl;
import com.decagon.loanagreementservice.dtos.response.LoanAgreementDto;
import com.decagon.loanagreementservice.exceptions.InvalidTokenException;
import com.decagon.loanagreementservice.exceptions.OfferNotFoundException;
import com.decagon.loanagreementservice.exceptions.UserNotAuthorizedException;
import com.decagon.loanagreementservice.models.LoanAgreement;
import com.decagon.loanagreementservice.models.LoanOffer;
import com.decagon.loanagreementservice.models.Status;
import com.decagon.loanagreementservice.repository.AgreementRepository;
import com.decagon.loanagreementservice.security_config.JwtUtils;
import com.decagon.loanagreementservice.services.BorrowerService;
import com.decagon.loanagreementservice.services.LoanOfferClient;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class BorrowerServiceImpl implements BorrowerService {
    private final LoanOfferClient loanOfferClient;
    private final AgreementRepository repository;
    private final JwtUtils jwtUtils;


    @Override
    public LoanAgreementDto selectLoanOffer(String loanId, HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UserNotAuthorizedException("permission denied");
        }

        String token = authHeader.substring(7);
        if (!jwtUtils.getUserTypeFromToken(token).equalsIgnoreCase("BORROWER")) {
            throw new UserNotAuthorizedException("permission denied");
        }
        String userId = jwtUtils.getUserIdFromToken(token);
        System.out.println("user Id from controller " + userId + ">>>>>>>>>>>>");
        if (userId == null) {
            throw new InvalidTokenException("UserId is null");
        }
        LoanOffer loanOffer = getLoanOffer(loanId);
        if (Objects.isNull(loanOffer)) {
            throw new OfferNotFoundException("loan offer not found");
        }
        // todo Set the status of the loan offer to pending. NB the loan application entity is present in a different service
        LoanAgreement loanAgreement = new LoanAgreement();
        loanAgreement.setInterestRate(loanOffer.getInterestRate());
        // todo get the id of the logged in user who in this case is a borrower and replace randomLong.
        loanAgreement.setBorrowerId(userId);
        loanAgreement.setLenderId(loanOffer.getLenderId());
        loanAgreement.setStatus(Status.PENDING);
        loanAgreement.setLoanId(loanOffer.getLoanId());
        loanAgreement.setDurationInDays(loanOffer.getDurationInDays());
        repository.save(loanAgreement);
        //            BeanUtils.copyProperties(loanAgreement, loanAgreementDto);
        return new LoanAgreementDto(loanAgreement);
    }


    public LoanOffer getLoanOffer(String offerId) {
        // todo Use ur FeignClient to call the loanOffer service
        ResponseEntity<LoanOffer> response = loanOfferClient.getLoanOffer(offerId);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            // Handle error case
            throw new RuntimeException("Failed to retrieve loan offer.");
        }
    }


}


