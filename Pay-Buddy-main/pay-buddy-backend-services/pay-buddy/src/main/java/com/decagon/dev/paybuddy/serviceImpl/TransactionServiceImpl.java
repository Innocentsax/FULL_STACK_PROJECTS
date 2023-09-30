package com.decagon.dev.paybuddy.serviceImpl;

import com.decagon.dev.paybuddy.dtos.responses.vtpass.response.data.TransactionResponse;
import com.decagon.dev.paybuddy.dtos.responses.vtpass.response.data.TransactionResponseViewModel;
import com.decagon.dev.paybuddy.models.Transaction;
import com.decagon.dev.paybuddy.models.Wallet;
import com.decagon.dev.paybuddy.repositories.TransactionRepository;
import com.decagon.dev.paybuddy.repositories.UserRepository;
import com.decagon.dev.paybuddy.repositories.WalletRepository;
import com.decagon.dev.paybuddy.services.TransactionService;
import com.decagon.dev.paybuddy.utilities.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;
    private final UserUtil userUtil;

    @Override
    public TransactionResponseViewModel viewWalletTransaction(int page, int limit) {

        String userEmail = userUtil.getAuthenticatedUserEmail();
        userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not Found"));

        Wallet wallet = walletRepository.findWalletByUser_Email(userEmail);

        if (page > 0) page -= 1;
        Pageable pageable = PageRequest.of(page, limit, Sort.by("transactionId").descending());
        Page<Transaction> pageList = transactionRepository.findAllByWallet(wallet, pageable);

        List<Transaction> transactionList = pageList.getContent();

        List<TransactionResponse> transactionResponse = TransactionResponse.mapFromTransaction(transactionList);
        return TransactionResponseViewModel.builder()
                .list(transactionResponse)
                .count(pageList.getNumberOfElements())
                .totalPage(pageList.getTotalPages())
                .build();

    }
}