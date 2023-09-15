package com.example.cedarxpressliveprojectjava010.service.implementation;

import com.example.cedarxpressliveprojectjava010.entity.BlacklistToken;
import com.example.cedarxpressliveprojectjava010.repository.BlacklistRepository;
import com.example.cedarxpressliveprojectjava010.service.BlacklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class BlacklistServiceImpl implements BlacklistService {
    private final BlacklistRepository blacklistRepository;

    @Override
    public BlacklistToken blackListToken(String token, Date date) {
        BlacklistToken blacklistToken = BlacklistToken.builder()
                                        .token(token)
                                        .expiresAt(date)
                                        .build();
        return blacklistRepository.save(blacklistToken);
    }

    @Override
    public boolean isTokenBlackListed(String token) {
        return blacklistRepository.existsByToken(token);
    }
}
