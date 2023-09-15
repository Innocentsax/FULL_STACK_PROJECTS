package com.example.cedarxpressliveprojectjava010.controller;

import com.example.cedarxpressliveprojectjava010.dto.EmailSenderDto;
import com.example.cedarxpressliveprojectjava010.service.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.mail.MessagingException;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class TestMailSender {

    private final EmailSenderService emailService;

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody EmailSenderDto emailSenderDto) throws MessagingException {
        return emailService.send(emailSenderDto);
    }
}




