package com.example.cedarxpressliveprojectjava010.service.implementation;

import com.example.cedarxpressliveprojectjava010.dto.EmailSenderDto;
import com.example.cedarxpressliveprojectjava010.service.EmailSenderService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService {

    private final static Logger LOGGER =  LoggerFactory.getLogger(EmailSenderService.class);

    private JavaMailSender mailSender;

    @Override
    @Async
    public ResponseEntity<String> send (EmailSenderDto emailSenderDto) {
        try{
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setTo(emailSenderDto.getTo());
            helper.setSubject(emailSenderDto.getSubject());
            helper.setText(emailSenderDto.getContent());
            mailSender.send(mimeMessage);
            return new ResponseEntity<>("Sent successfully", HttpStatus.OK);
        } catch (MessagingException e) {
            LOGGER.error("failed to send email", e);
            throw new IllegalStateException("failed to send email");
        }

    }
}
