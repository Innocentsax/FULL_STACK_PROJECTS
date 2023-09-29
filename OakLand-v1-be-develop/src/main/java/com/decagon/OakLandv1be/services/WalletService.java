package com.decagon.OakLandv1be.services;

import com.decagon.OakLandv1be.dto.FundWalletRequest;
import com.decagon.OakLandv1be.dto.FundWalletResponseDto;
import com.decagon.OakLandv1be.dto.TransactionResponseDto;
import com.decagon.OakLandv1be.dto.WalletInfoResponseDto;
import com.decagon.OakLandv1be.utils.ApiResponse;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface WalletService {

    FundWalletResponseDto fundWallet(FundWalletRequest request);

    BigDecimal getWalletBalance();

    Boolean processPayment(BigDecimal grandTotal);

    WalletInfoResponseDto viewWalletInfo();

    Page<TransactionResponseDto> fetchAllTransactions(Integer pageNo, Integer pageSize, String sortBy);

    Page<FundWalletResponseDto> viewCustomerWalletByPagination(Integer pageNo, Integer pageSize, String sortBy);
}
