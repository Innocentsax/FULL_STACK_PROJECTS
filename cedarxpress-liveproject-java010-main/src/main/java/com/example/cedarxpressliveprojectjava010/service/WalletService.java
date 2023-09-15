package com.example.cedarxpressliveprojectjava010.service;

import com.example.cedarxpressliveprojectjava010.dto.WalletDto;
import com.example.cedarxpressliveprojectjava010.dto.request.FundWalletRequest;
import com.example.cedarxpressliveprojectjava010.dto.request.WalletWithdrawalRequest;
import com.example.cedarxpressliveprojectjava010.entity.Wallet;
import org.springframework.http.ResponseEntity;

public interface WalletService {

    ResponseEntity<Wallet> fundWallet (FundWalletRequest fundWalletRequest);
    ResponseEntity<WalletDto> checkBalance(long id);
    ResponseEntity<Wallet> walletWithdrawal(WalletWithdrawalRequest walletWithdrawalRequest);
}
