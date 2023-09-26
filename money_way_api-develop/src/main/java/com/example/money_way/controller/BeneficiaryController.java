package com.example.money_way.controller;

import com.example.money_way.dto.response.ApiResponse;
import com.example.money_way.model.Beneficiary;
import com.example.money_way.service.BeneficiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/beneficiaries")
public class BeneficiaryController {
    private final BeneficiaryService beneficiaryService;

    @GetMapping()
    public ResponseEntity<ApiResponse<List<Beneficiary>>> getBeneficiaries(
            @RequestParam("type") String transactionType){
        return ResponseEntity.ok(beneficiaryService.getBeneficiaries(transactionType));
    }
}
