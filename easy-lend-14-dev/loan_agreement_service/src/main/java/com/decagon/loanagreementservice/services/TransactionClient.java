package com.decagon.loanagreementservice.services;

import com.decagon.loanagreementservice.dtos.request.TransactionRequest;
import com.decagon.loanagreementservice.dtos.response.TransactionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
@Component
@FeignClient(name = "${transaction-api.name}", url = "${transaction-api.base-url}")
public interface TransactionClient {
    @GetMapping("api/v1/transaction/initialize-payment")
    ResponseEntity<TransactionResponse> getTransactionRequest(@RequestBody TransactionRequest transactionRequest);
}
