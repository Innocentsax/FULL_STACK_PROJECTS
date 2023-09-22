package com.decagon.fintechpaymentapisqd11a.services;

import com.decagon.fintechpaymentapisqd11a.token.ConfirmationToken;

import java.util.Optional;

public interface ConfirmationTokenService {
    void saveConfirmationToken(ConfirmationToken token);
    Optional<ConfirmationToken> getToken(String token);
    int setConfirmedAt(String token);
}
