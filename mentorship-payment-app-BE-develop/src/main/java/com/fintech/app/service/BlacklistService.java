package com.fintech.app.service;

import com.fintech.app.model.BlacklistedToken;

public interface BlacklistService {
    BlacklistedToken blacklistToken(String token);
    boolean tokenExist(String token);
}
