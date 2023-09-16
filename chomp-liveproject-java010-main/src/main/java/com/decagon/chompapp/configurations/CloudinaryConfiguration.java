package com.decagon.chompapp.configurations;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfiguration {

    @Value("deenn")
    private String cloudName;

    @Value("433122499314656")
    private String apiKey;

    @Value("WcgU8BKOSR-C2tI1sNKZ9t4HwXQ")
    private String apiSecret;

    @Bean
    public Cloudinary cloudinaryConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put("cloud_name", cloudName);
        config.put("api_key", apiKey);
        config.put("api_secret", apiSecret);
        config.put("secure", true);
        return new Cloudinary(config);
    }
}

