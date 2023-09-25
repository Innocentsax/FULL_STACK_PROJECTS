package com.decadev.money.way.service;

import com.decadev.money.way.dto.request.VirtualAccountCreationRequest;
import com.decadev.money.way.dto.response.VirtualAccountCreationResponse;
import com.decadev.money.way.model.User;
import com.decadev.money.way.model.Wallet;

import java.io.IOException;

public interface WalletService {

    Wallet createWallet(VirtualAccountCreationResponse response, User user);
    VirtualAccountCreationResponse createVirtualAcc(VirtualAccountCreationRequest request) throws IOException;
}
