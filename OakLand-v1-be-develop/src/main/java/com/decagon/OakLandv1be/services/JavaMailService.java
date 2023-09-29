package com.decagon.OakLandv1be.services;

import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface JavaMailService {
    ResponseEntity<String>sendMail(String receiverEmail, String subject, String text) throws IOException;
    ResponseEntity<String>sendMailAlt(String receiverEmail, String subject, String text) throws IOException;

}
