package com.goCash.services.implementations;

import com.goCash.dto.request.SendEmailDto;
import com.goCash.services.EmailServices;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@Async
@RequiredArgsConstructor
public class EmailServicesImpl implements EmailServices {
    private final static Logger LOGGER = LoggerFactory.getLogger(EmailServices.class);

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String GoCashEmail;

    @Async
    @Override
    public void sendMessage(SendEmailDto request) {
        try {

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(request.getMessage(), true);
            helper.setTo(request.getToEmail());
            helper.setSubject(request.getSubject());
            helper.setFrom(GoCashEmail);
            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            LOGGER.error("failed to send email", e);
            throw new IllegalStateException("failed to send email");
        }
    }
}



