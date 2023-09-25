package com.fintech.app.service;

import com.fintech.app.model.FlwBank;
import com.fintech.app.request.FlwAccountRequest;
import com.fintech.app.request.TransferRequest;
import com.fintech.app.request.VerifyTransferRequest;
import com.fintech.app.response.BaseResponse;
import com.fintech.app.response.FlwAccountResponse;
import com.fintech.app.response.OtherBankTransferResponse;
import com.fintech.app.response.VerifyTransferResponse;

import java.util.List;

public interface OtherBankTransferService {

    BaseResponse<OtherBankTransferResponse> initiateOtherBankTransfer(TransferRequest transferRequest);
    List<FlwBank> getBanks();
    BaseResponse<FlwAccountResponse> resolveAccount(FlwAccountRequest flwAccountRequest);
    BaseResponse<VerifyTransferResponse> verifyTransaction(VerifyTransferRequest verifyTransferRequest);
}
