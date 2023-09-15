package com.example.cedarxpressliveprojectjava010.service;

import com.example.cedarxpressliveprojectjava010.dto.EmailSenderDto;
import org.springframework.http.ResponseEntity;
import javax.mail.MessagingException;

public interface EmailSenderService {
    ResponseEntity<String> send(EmailSenderDto emailSenderDto) throws MessagingException;
}
