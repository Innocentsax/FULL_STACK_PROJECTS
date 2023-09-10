package com.goCash.services;

import com.goCash.dto.request.AccountVerificationRequest;
import com.goCash.dto.request.LocalTransferRequest;
import com.goCash.entities.WalletAccount;
import org.springframework.stereotype.Service;


import com.goCash.utils.ApiResponse;

@Service
public interface WalletServices {

    ApiResponse fundWallet();
    ApiResponse<Double> balanceEnquiry();

    ApiResponse walletToWalletTransfer(LocalTransferRequest localTransferRequest);

    ApiResponse verifyLocalAccount(AccountVerificationRequest request);
}
