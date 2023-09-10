package com.hrsupportcentresq014.services.serviceImpl;

import com.hrsupportcentresq014.services.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class MailServiceImpl implements MailService {
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String email;
    public MailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    @Override
    public void sendMailTest(String senderEmail, String messageSubject, String messageBody){
        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper messageController = null;
        try {
            messageController = new MimeMessageHelper(
                    message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());


            messageController.setFrom(email);
            messageController.setTo(senderEmail);
            messageController.setSubject(messageSubject);
            messageController.setText(messageBody);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendAccountActivation(String receiverEmail, String activationUrl){
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageController = null;
        try {
            messageController = new MimeMessageHelper(
                    message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name()
            );


            messageController.setTo(receiverEmail);
            messageController.setSubject("Welcome to H.R.M.S ");
            messageController.setText(activationUrl);
            messageController.setFrom(email);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void passwordReset(String resetUrl, String receiverEmail){
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageController = null;
        try {
            messageController = new MimeMessageHelper(
                    message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name()
            );


            messageController.setTo(receiverEmail);
            messageController.setSubject("Password Reset");
            messageController.setText(resetUrl, true);
            messageController.setFrom(email);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
