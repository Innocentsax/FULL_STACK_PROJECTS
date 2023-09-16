package com.decagon.chompapp.services.Impl;

import com.decagon.chompapp.models.BlackListedToken;
import com.decagon.chompapp.repositories.BlackListedTokenRepository;
import com.decagon.chompapp.services.BlackListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlackListTokenImpl implements BlackListService {

    private final BlackListedTokenRepository blackListedTokenRepository;

    @Override
    public BlackListedToken blackListToken(String token) {

        BlackListedToken blackListedToken = new BlackListedToken();
        blackListedToken.setToken(token.substring(7));

        return blackListedTokenRepository.save(blackListedToken);
    }

    @Override
    public boolean tokenExist(String token){
        return blackListedTokenRepository.existsByToken(token);
    }
}

