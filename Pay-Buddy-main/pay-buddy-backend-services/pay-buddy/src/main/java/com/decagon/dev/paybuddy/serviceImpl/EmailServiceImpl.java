package com.decagon.dev.paybuddy.serviceImpl;

import com.decagon.dev.paybuddy.dtos.requests.EmailSenderDto;
import com.decagon.dev.paybuddy.services.EmailService;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Objects;

@Service
@Component
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

    private JavaMailSender emailSender;
    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    @Override
    public void sendMail(EmailSenderDto emailSenderDto){
        if (
                (Objects.nonNull(emailSenderDto.getTo())) &&
                        (Objects.nonNull(emailSenderDto.getSubject())) &&
                        (Objects.nonNull(emailSenderDto.getContent()))
        ) {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "utf-8");
            try {
                LOGGER.info("Transferring Data from EmailSenderDto to MimeMessage helper");

                message.setTo(emailSenderDto.getTo());
                message.setSubject(emailSenderDto.getSubject());
                message.setText(emailSenderDto.getContent(),true);

                emailSender.send(mimeMessage);

            } catch (MessagingException e) {

                LOGGER.error("An error occurred while sending an email to address : " + emailSenderDto.getTo() + "; error: " + e.getMessage());

            }
            LOGGER.info("Mail has been sent");
        }
    }

}
