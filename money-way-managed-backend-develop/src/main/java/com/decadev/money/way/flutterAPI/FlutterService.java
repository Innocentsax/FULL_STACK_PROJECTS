package com.decadev.money.way.flutterAPI;

import com.decadev.money.way.dto.request.CashTransferRequest;
import com.decadev.money.way.dto.request.FundWalletRequest;
import com.decadev.money.way.dto.response.VirtualAccountCreationResponse;
import com.decadev.money.way.exception.UserNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;


public interface FlutterService {
    VirtualAccountCreationResponse bankAccountCharge(FundWalletRequest request) throws JsonProcessingException, UserNotFoundException;

    Object transferToOtherBank(CashTransferRequest request) throws JsonProcessingException;
}
