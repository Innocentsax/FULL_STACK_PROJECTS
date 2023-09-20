package com.decagon.lendingservice.service;

import com.decagon.lendingservice.dto.InvestmentDTORequest;
import com.decagon.lendingservice.dto.InvestmentDTOResponse;
import com.decagon.lendingservice.entity.InvestmentPreference;

import java.util.Optional;

public interface InvestmentPreferenceService {
    InvestmentDTOResponse createInvestment(InvestmentDTORequest request, String token);


    InvestmentDTOResponse getLoanOffer(String id);
}
