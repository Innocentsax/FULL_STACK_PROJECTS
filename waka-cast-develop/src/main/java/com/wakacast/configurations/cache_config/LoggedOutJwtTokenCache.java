package com.wakacast.configurations.cache_config;

import com.wakacast.configurations.security_configs.JwtTokenUtil;
import com.wakacast.event.OnUserLogoutSuccessEvent;
import lombok.extern.slf4j.Slf4j;
import net.jodah.expiringmap.ExpiringMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class LoggedOutJwtTokenCache implements Serializable {
    private static final long serialVersionUID= -34556789012344367L;
    private final transient ExpiringMap<String, OnUserLogoutSuccessEvent> tokenEventMap;
    @Lazy
    private final JwtTokenUtil tokenProvider;

    @Autowired
    public LoggedOutJwtTokenCache(JwtTokenUtil tokenProvider) {
        this.tokenProvider = tokenProvider;
        this.tokenEventMap = ExpiringMap.builder()
                .variableExpiration()
                .maxSize(1000)
                .build();
    }

    public void markLogoutEventForToken(OnUserLogoutSuccessEvent event) {
        String token = event.getToken();
        if (tokenEventMap.containsKey(token)) {
            log.info(String.format("Log out token for user [%s] is already present in the cache", event.getEmail()));

        } else {
            Date tokenExpiryDate = tokenProvider.getExpirationDateFromToken(token);
            long ttlForToken = getTTLForToken(tokenExpiryDate);
            log.info(String.format("Logout token cache set for [%s] with a TTL of [%s] seconds. Token is due expiry at [%s]", event.getEmail(), ttlForToken, tokenExpiryDate));
            tokenEventMap.put(token, event, ttlForToken, TimeUnit.SECONDS);
        }
    }

    public OnUserLogoutSuccessEvent getLogoutEventForToken(String token) {
        return tokenEventMap.get(token);
    }

    private long getTTLForToken(Date date) {
        long secondAtExpiry = date.toInstant().getEpochSecond();
        long secondAtLogout = Instant.now().getEpochSecond();
        return Math.max(0, secondAtExpiry - secondAtLogout);
    }
}
