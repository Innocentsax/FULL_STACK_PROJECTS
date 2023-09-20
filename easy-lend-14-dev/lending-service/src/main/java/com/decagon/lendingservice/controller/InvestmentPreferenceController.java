package com.decagon.lendingservice.controller;

import com.decagon.lendingservice.dto.InvestmentDTORequest;
import com.decagon.lendingservice.dto.InvestmentDTOResponse;
import com.decagon.lendingservice.service.InvestmentPreferencePagination;
import com.decagon.lendingservice.service.InvestmentPreferenceService;
import com.decagon.lendingservice.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class InvestmentPreferenceController {
    private final InvestmentPreferenceService investmentPreferenceService;
    private  final InvestmentPreferencePagination pagination;
    private final JwtUtils jwtUtils;

    @PostMapping("/create")
    public ResponseEntity<InvestmentDTOResponse> createInvestment(@RequestBody InvestmentDTORequest request, @RequestHeader ("Authorization") String authorizationHeader){
        String token = extractToken(authorizationHeader);

       InvestmentDTOResponse invest = investmentPreferenceService.createInvestment(request, token );
       return  new ResponseEntity<>(invest, HttpStatus.OK);
    }
    @GetMapping("/getloan/{id}")
    public ResponseEntity<InvestmentDTOResponse> getLoanOffer(@PathVariable String id){
        InvestmentDTOResponse getLoans  = investmentPreferenceService.getLoanOffer(id);
        return new ResponseEntity<>(getLoans, HttpStatus.OK);
    }
    @GetMapping("/investment")
    public ResponseEntity<Page<InvestmentDTOResponse>> investmentPage(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize){
        Page<InvestmentDTOResponse> paginated =  pagination.getPaginatedInvestment(page, pageSize);
        return new ResponseEntity<>(paginated,HttpStatus.OK);
    }
    private String extractToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring("Bearer ".length());
        }
        return null;
    }
}
