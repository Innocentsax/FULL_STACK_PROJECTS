package com.example.money_way.service;

import com.example.money_way.dto.response.ApiResponse;
import com.example.money_way.model.Beneficiary;

import java.util.List;

public interface BeneficiaryService {
    ApiResponse<List<Beneficiary>> getBeneficiaries(String transactionType);
}
