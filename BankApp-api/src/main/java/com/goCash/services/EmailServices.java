package com.goCash.services;


import com.goCash.dto.request.SendEmailDto;
import org.springframework.stereotype.Component;

@Component
public interface EmailServices {
    void sendMessage(SendEmailDto data);
}
