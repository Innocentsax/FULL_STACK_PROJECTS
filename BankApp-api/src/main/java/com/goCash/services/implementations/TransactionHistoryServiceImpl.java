package com.goCash.services.implementations;

import com.goCash.entities.Transaction;
import com.goCash.entities.User;
import com.goCash.exception.UserNotFoundException;
import com.goCash.repository.TransactionHistoryRepository;
import com.goCash.repository.UserRepository;
import com.goCash.services.TransactionHistoryService;
import com.goCash.utils.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionHistoryServiceImpl implements TransactionHistoryService {
    private final UserRepository userRepository;
    private final TransactionHistoryRepository transactionHistoryRepository;
    private final Util util;
    @Override
    public Page<Transaction> generateTransactionHistory(int page, int pageSize) {
        User user = userRepository.findByEmail(util.getLoginUser()).orElseThrow(()-> new UserNotFoundException("User does not exist"));
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Transaction> transactionPage = transactionHistoryRepository.findTransactionsByUser(user,pageable);
        return transactionPage;

    }
}
