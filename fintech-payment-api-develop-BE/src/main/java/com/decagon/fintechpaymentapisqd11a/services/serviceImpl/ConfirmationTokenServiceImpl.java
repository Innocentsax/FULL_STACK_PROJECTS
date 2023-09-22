package com.decagon.fintechpaymentapisqd11a.services.serviceImpl;

import com.decagon.fintechpaymentapisqd11a.repositories.ConfirmationTokenRepository;
import com.decagon.fintechpaymentapisqd11a.services.ConfirmationTokenService;
import com.decagon.fintechpaymentapisqd11a.token.ConfirmationToken;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
@AllArgsConstructor
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;
    @Override
    public void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }
    @Override
    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }
    @Override
    public int setConfirmedAt(String token) {
        return confirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
    }
}
