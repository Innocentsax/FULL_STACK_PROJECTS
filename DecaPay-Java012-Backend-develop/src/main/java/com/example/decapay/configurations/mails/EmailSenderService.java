package com.example.decapay.configurations.mails;

import com.example.decapay.pojos.mailDto.MailDto;
import org.springframework.http.ResponseEntity;

public interface EmailSenderService {
    ResponseEntity<String> sendEmail(MailDto mailDto);

    void sendPasswordResetEmail(String name, String email, String link);
}
