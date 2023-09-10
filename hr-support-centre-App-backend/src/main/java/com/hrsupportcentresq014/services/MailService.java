package com.hrsupportcentresq014.services;


public interface MailService {
    void sendMailTest(String senderEmail,String messageSubject, String messageBody);
    void sendAccountActivation(String receiverEmail, String activationUrl);
    void passwordReset(String resetUrl, String receiverEmail);
}
