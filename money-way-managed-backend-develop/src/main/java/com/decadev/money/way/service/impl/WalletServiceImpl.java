package com.decadev.money.way.service.impl;

import com.decadev.money.way.dto.request.VirtualAccountCreationRequest;
import com.decadev.money.way.dto.response.VirtualAccountCreationResponse;
import com.decadev.money.way.flutterAPI.VirtualAccountClient;
import com.decadev.money.way.model.User;
import com.decadev.money.way.model.Wallet;
import com.decadev.money.way.repository.WalletRepository;
import com.decadev.money.way.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;
    private final VirtualAccountClient virtualAccountClient;
    @Override
    public Wallet createWallet(VirtualAccountCreationResponse response, User user) {

        Wallet newWallet = Wallet.builder()
                .accountBalance(response.getData().getAmount())
                .active(true)
                .accountNumber(response.getData().getAccount_number())
                .bankName(response.getData().getBank_name())
                .user(user)
                .build();

        return walletRepository.save(newWallet);
    }

    public VirtualAccountCreationResponse createVirtualAcc(VirtualAccountCreationRequest request) throws IOException {
        return (VirtualAccountCreationResponse) virtualAccountClient.createVirtualAccount(request);
    }
}
