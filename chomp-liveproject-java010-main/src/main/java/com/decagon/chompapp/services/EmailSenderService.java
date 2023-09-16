package com.decagon.chompapp.services;

import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;

import com.decagon.chompapp.dtos.EmailSenderDto;
import org.springframework.http.ResponseEntity;

import javax.mail.MessagingException;

public interface EmailSenderService {
    ResponseEntity<String> send(EmailSenderDto emailSenderDto) throws MessagingException;
    void sendRegistrationEmail(String email, String token) throws MalformedURLException;
}
