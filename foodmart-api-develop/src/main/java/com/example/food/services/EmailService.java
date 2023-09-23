package com.example.food.services;

import com.example.food.dto.EmailSenderDto;

public interface EmailService {
    void sendMail(EmailSenderDto emailSenderDto);
}
