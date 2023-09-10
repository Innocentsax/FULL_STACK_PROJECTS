package com.goCash.services;

import com.goCash.dto.request.BankNamesRequest;

import java.util.List;

public interface BankNamesService {

        List<BankNamesRequest> getAllBanks();
    }

