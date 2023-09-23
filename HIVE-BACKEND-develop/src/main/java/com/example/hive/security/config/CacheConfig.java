package com.example.hive.security.config;

import com.example.hive.dto.response.ListBanksResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.Cache;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;

@Configuration
public class CacheConfig {
    @Bean
    public Cache<String, ListBanksResponse> listBanksResponseCache() {

        MutableConfiguration<String, ListBanksResponse> configuration = new MutableConfiguration<String, ListBanksResponse>()
                .setTypes(String.class, ListBanksResponse.class)
                .setStoreByValue(false);

        return Caching
                .getCachingProvider()
                .getCacheManager()
                .createCache("LIST_BANKS_CACHE", configuration);
    }
}
