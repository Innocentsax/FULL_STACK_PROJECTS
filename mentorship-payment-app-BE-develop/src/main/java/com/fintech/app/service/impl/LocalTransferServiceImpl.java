package com.fintech.app.service.impl;

import com.fintech.app.model.Transfer;
import com.fintech.app.model.User;
import com.fintech.app.model.Wallet;
import com.fintech.app.repository.TransferRepository;
import com.fintech.app.repository.UserRepository;
import com.fintech.app.repository.WalletRepository;
import com.fintech.app.request.LocalTransferRequest;
import com.fintech.app.response.BaseResponse;
import com.fintech.app.service.LocalTransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class LocalTransferServiceImpl implements LocalTransferService {
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final TransferRepository transferRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    @Override
    public BaseResponse<Transfer> makeLocalTransfer(LocalTransferRequest transferRequest) {

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findUserByEmail(userEmail);
        if (user == null) {
            return new BaseResponse<>(HttpStatus.UNAUTHORIZED, "User not logged in", null);
        }

        Wallet recipientWallet = walletRepository.findWalletByAccountNumber(transferRequest.getAccountNumber());
        if (recipientWallet == null) {
            return new BaseResponse<>(HttpStatus.NOT_FOUND, "Recipient Wallet not found", null);
        }
        User recipient = recipientWallet.getUser();
        if (recipient == null) {
            return new BaseResponse<>(HttpStatus.NOT_FOUND, "Recipient not found", null);
        } else  if (user == recipient) {
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, "Fraudulent action, can't transfer to self", null);
        }

        Wallet userWallet = walletRepository.findWalletByUser(user);
        if (userWallet == null) {
            return new BaseResponse<>(HttpStatus.NOT_FOUND, "Sender Wallet not found", null);
        }

        Double transferAmount = transferRequest.getAmount();
        Double userWalletBalance = userWallet.getBalance();
        if (transferAmount < 1) {
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, "Invalid transfer amount", null);
        }
        if (transferAmount > userWalletBalance) {
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, "Insufficient funds", null);
        }

        if (!passwordEncoder.matches(transferRequest.getPin(), user.getPin())) {
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, "Incorrect transfer pin", null);
        }

        String recipientName = recipient.getFirstName() + " " + recipient.getLastName();

        Transfer newTransfer = Transfer.builder()
                .senderFullName(user.getFirstName() + " " + user.getLastName())
                .senderAccountNumber(userWallet.getAccountNumber())
                .senderBankName(userWallet.getBankName())
                .destinationFullName(recipientName)
                .destinationAccountNumber(transferRequest.getAccountNumber())
                .destinationBank(recipientWallet.getBankName())
                .amount(transferRequest.getAmount())
                .clientRef(UUID.randomUUID().toString())
                .narration(transferRequest.getNarration())
                .type("LOCAL")
                .status("PENDING")
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();

        transferRepository.save(newTransfer);

        double recipientWalletBalance = recipientWallet.getBalance();

        userWallet.setBalance(userWalletBalance - transferAmount);

        recipientWallet.setBalance(recipientWalletBalance + transferAmount);

        walletRepository.save(userWallet);
        walletRepository.save(recipientWallet);

        newTransfer.setStatus("SUCCESSFUL");
        newTransfer.setModifyAt(LocalDateTime.now());

        transferRepository.save(newTransfer);

        return new BaseResponse<>(HttpStatus.OK, "Transfer to " + recipientName + " successful", newTransfer);
    }

    @Override
    public BaseResponse<String> resolveLocalAccount(String accountNumber) {
        Wallet wallet = walletRepository.findWalletByAccountNumber(accountNumber);

        if(wallet != null){
            String accountName = wallet.getUser().getFirstName() + " "+ wallet.getUser().getLastName();
            return new BaseResponse<>(HttpStatus.OK, "account retrieved",accountName);
        } else {
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, "Account not found", null);
        }
    }

}