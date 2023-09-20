package com.decagon.lendingservice.service.serviceImpl;


import com.decagon.lendingservice.entity.InvestmentPreference;


import com.decagon.lendingservice.exceptions.BorrowersNotAllowedException;
import com.decagon.lendingservice.exceptions.InvestmentPreferenceExistsException;
import com.decagon.lendingservice.dto.InvestmentDTORequest;
import com.decagon.lendingservice.dto.InvestmentDTOResponse;
import com.decagon.lendingservice.repo.InvestmentPreferenceRepository;
import com.decagon.lendingservice.service.InvestmentPreferenceService;
import com.decagon.lendingservice.utils.JwtUtils;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InvestmentPreferenceServiceImpl implements InvestmentPreferenceService {
    private final InvestmentPreferenceRepository investmentRepository;
    private final ModelMapper modelMapper;
    private  final JwtUtils jwtUtils;

    @Override
    public InvestmentDTOResponse createInvestment(InvestmentDTORequest request, String token) {
        String loanId = UUID.randomUUID().toString();
      if(request.getLoanAmount().compareTo(BigDecimal.ZERO ) <= 0){
          throw  new ValidationException("loan amount must be greater than 0");
      }
      String userType = jwtUtils.getUserTypeFromToken(token);
      if("borrower".equalsIgnoreCase(userType)){
          throw   new BorrowersNotAllowedException("Borrowers are not Allowed to create an Investment");
      }
      String userId = jwtUtils.extractUserIdFromToken(token);

      InvestmentPreference response = modelMapper.map(request, InvestmentPreference.class);

          response.setUserId(userId);
          response.setLoanId(loanId);
          return new InvestmentDTOResponse(investmentRepository.save(response));
   }

    @Override
    public InvestmentDTOResponse getLoanOffer(String id) {
        InvestmentPreference investmentPreference = investmentRepository.findInvestmentPreferenceById(id).orElseThrow(() -> new RuntimeException("No ID Found"));
        return  new InvestmentDTOResponse(investmentPreference);
    }

}
