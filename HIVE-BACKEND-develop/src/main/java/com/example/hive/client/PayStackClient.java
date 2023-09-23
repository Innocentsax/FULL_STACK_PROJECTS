package com.example.hive.client;

import com.example.hive.constant.AppConstants;
import com.example.hive.dto.request.PayStackTransferRecepientRequest;
import com.example.hive.dto.request.PayStackTransferRequest;
import com.example.hive.dto.response.TransactionResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Flux;

@HttpExchange
public interface PayStackClient {


    @GetExchange(value = AppConstants.PSTK_LIST_BANKS_URI)
    TransactionResponse<Object> listBanks();

    @PostExchange(value = AppConstants.PSTK_TRANSFER_RECIPIENT_URI)
    Object createTransferRecipient(@RequestBody PayStackTransferRecepientRequest request);

    @PostExchange(value = AppConstants.PSTK_TRANSFER_URI)
    Flux<TransactionResponse> transferFunds(@RequestBody PayStackTransferRequest request, @RequestHeader("Authorization") String authorizationHeader);


}
