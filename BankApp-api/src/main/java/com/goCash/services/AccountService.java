package com.goCash.services;

import com.goCash.dto.request.AccountVerificationRequest;
import com.goCash.dto.response.FlutterWaveApiResponse;


public interface AccountService {
    FlutterWaveApiResponse bankVerification(AccountVerificationRequest apiRequest);
}
