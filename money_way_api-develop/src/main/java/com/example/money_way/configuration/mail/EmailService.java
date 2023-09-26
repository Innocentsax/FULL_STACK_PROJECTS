package com.example.money_way.configuration.mail;

public interface EmailService {
    void sendEmail(String to, String subject, String message);
}
