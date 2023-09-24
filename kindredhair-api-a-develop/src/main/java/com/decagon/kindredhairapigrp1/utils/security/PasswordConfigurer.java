package com.decagon.kindredhairapigrp1.utils.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Creates a BCryptPasswordEncoder configuration bean for  the App
 */
@Configuration
public class PasswordConfigurer{
    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder(12);
    }
}
