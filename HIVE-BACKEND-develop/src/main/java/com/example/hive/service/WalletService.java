package com.example.hive.service;

import com.example.hive.dto.response.TransactionResponse;
import com.example.hive.entity.Task;
import com.example.hive.entity.User;

import java.math.BigDecimal;
import com.example.hive.dto.response.WalletResponseDto;
import com.example.hive.entity.Wallet;

import java.security.Principal;
import java.util.List;

public interface WalletService {



    boolean creditDoerWallet(User doer, BigDecimal creditAmount);

    WalletResponseDto getWalletByUser(Principal principal);

    void withdrawFromWalletBalance(User user, BigDecimal amount);

    boolean fundTaskerWallet(User tasker, BigDecimal amountToFund);

    boolean debitTaskerWalletToEscrow(Wallet wallet, BigDecimal amount);

    boolean refundTaskerFromEscrowWallet(Task taskToCancel);

    List<TransactionResponse> getWalletHistory(Principal principal);
}
