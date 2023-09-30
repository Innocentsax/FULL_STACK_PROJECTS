package com.decagon.dev.paybuddy.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author Ikechi Ucheagwu
 * @created 08/03/2023 - 10:52
 * @project Pay-Buddy
 */

@Configuration
public class BeansConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
