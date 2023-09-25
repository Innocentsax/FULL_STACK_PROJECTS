package com.fintech.app.service;


import com.fintech.app.model.Transfer;
import com.fintech.app.request.LocalTransferRequest;
import com.fintech.app.response.BaseResponse;

public interface LocalTransferService {

    BaseResponse<Transfer> makeLocalTransfer(LocalTransferRequest transferRequest);
    BaseResponse<String> resolveLocalAccount(String accountNumber);
}
