package com.decagon.adire.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@RequiredArgsConstructor
public class EmailClient {
    private final JavaMailSender emailSender;

    @Async
    public void sendMail(String to, String subject, String text) throws MessagingException {

        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("adire@resavation.com");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text, true);

        emailSender.send(message);

    }


}
