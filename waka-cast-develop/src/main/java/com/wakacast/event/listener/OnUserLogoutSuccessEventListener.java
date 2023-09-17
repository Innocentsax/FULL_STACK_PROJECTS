package com.wakacast.event.listener;

import com.wakacast.configurations.cache_config.LoggedOutJwtTokenCache;
import com.wakacast.event.OnUserLogoutSuccessEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OnUserLogoutSuccessEventListener implements ApplicationListener<OnUserLogoutSuccessEvent> {

    private final LoggedOutJwtTokenCache tokenCache;

    @Autowired
    public OnUserLogoutSuccessEventListener(LoggedOutJwtTokenCache tokenCache) {
        this.tokenCache = tokenCache;
    }

    public void onApplicationEvent(OnUserLogoutSuccessEvent event) {
        if (null != event) {
            log.info(String.format("Log out success event received for user [%s]", event.getEmail()));
            tokenCache.markLogoutEventForToken(event);
        }
    }
}
