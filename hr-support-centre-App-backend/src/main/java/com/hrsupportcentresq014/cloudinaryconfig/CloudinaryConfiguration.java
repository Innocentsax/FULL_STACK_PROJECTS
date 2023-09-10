package com.hrsupportcentresq014.cloudinaryconfig;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfiguration {
    @Value("${application.cloudinary.cloud_name}")
    private  String CLOUD_NAME;
    @Value("${application.cloudinary.Api_key}")
    private String API_KEY;
    @Value("${application.cloudinary.Api_secret}")
    private  String API_SECRET ;

    @Bean
    public Cloudinary cloudinaryConfig() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", CLOUD_NAME);
        config.put("api_key", API_KEY);
        config.put("api_secret", API_SECRET);
        return new Cloudinary(config);

    }
}
