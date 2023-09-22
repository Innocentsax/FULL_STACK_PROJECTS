package com.decagon.fintechpaymentapisqd11a.services;

import com.decagon.fintechpaymentapisqd11a.dto.WalletDto;
import com.decagon.fintechpaymentapisqd11a.exceptions.UserNotFoundException;
import com.decagon.fintechpaymentapisqd11a.models.Wallet;
import com.decagon.fintechpaymentapisqd11a.request.FlwWalletRequest;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Service;


@Service
public interface WalletService {
    WalletDto viewWalletDetails() throws UserNotFoundException;

    Wallet createWallet(FlwWalletRequest walletRequest) throws JSONException;

}
