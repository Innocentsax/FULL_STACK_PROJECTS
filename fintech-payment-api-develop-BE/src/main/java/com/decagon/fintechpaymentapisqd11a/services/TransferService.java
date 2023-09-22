package com.decagon.fintechpaymentapisqd11a.services;

import com.decagon.fintechpaymentapisqd11a.models.FlwBank;
import com.decagon.fintechpaymentapisqd11a.models.Transaction;
import com.decagon.fintechpaymentapisqd11a.models.Users;
import com.decagon.fintechpaymentapisqd11a.request.ExternalBankTransferRequest;
import com.decagon.fintechpaymentapisqd11a.request.FlwResolveAccountRequest;
import com.decagon.fintechpaymentapisqd11a.response.FlwGetAllBanksResponse;
import com.decagon.fintechpaymentapisqd11a.response.FlwOtherBankTransferResponse;
import com.decagon.fintechpaymentapisqd11a.response.FlwResolveAccountDetails;

import java.math.BigDecimal;
import java.util.List;


public interface TransferService {
    List<FlwBank> getAllBanks();

    FlwResolveAccountDetails resolveAccount(FlwResolveAccountRequest resolveAccountRequest);

    Users retrieveUserDetails();

    boolean validatePin(String pin, Users users);

    boolean validateRequestBalance(BigDecimal requestAmount);

    boolean validateWalletBalance(BigDecimal requestAmount, Users users);

    FlwOtherBankTransferResponse initiateOtherBankTransfer(ExternalBankTransferRequest transferRequest);

    FlwOtherBankTransferResponse otherBankTransfer(ExternalBankTransferRequest transferRequest, String clientRef);

    Transaction saveTransactions (Users users, ExternalBankTransferRequest transferRequest);
}
