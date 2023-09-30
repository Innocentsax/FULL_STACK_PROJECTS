package com.decagon.dev.paybuddy.services;

import com.decagon.dev.paybuddy.dtos.requests.EmailSenderDto;

public interface EmailService {
    void sendMail(EmailSenderDto emailSenderDto);

}
