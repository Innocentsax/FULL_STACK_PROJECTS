package com.decagon.loanagreementservice.services;

import com.decagon.loanagreementservice.models.LoanOffer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@Component
@FeignClient(name = "${loan-offer-api.name}", url = "${loan-offer-api.base-url}")
public interface LoanOfferClient {

    @GetMapping("api/v1/getloan/{id}")
    ResponseEntity<LoanOffer> getLoanOffer(@PathVariable("id") String loanId);
}