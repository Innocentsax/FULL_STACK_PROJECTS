package com.decagon.fintechpaymentapisqd11a.services;

import com.decagon.fintechpaymentapisqd11a.dto.LocalBankTransferDto;

public interface LocalTransferService {

    public String localTransfer(final LocalBankTransferDto localBankTransferDto);
}
