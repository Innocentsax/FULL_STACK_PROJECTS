package com.decagon.dev.paybuddy.services;


import com.decagon.dev.paybuddy.dtos.responses.vtpass.response.data.TransactionResponseViewModel;


public interface TransactionService {
    TransactionResponseViewModel viewWalletTransaction(int page, int limit);
}
