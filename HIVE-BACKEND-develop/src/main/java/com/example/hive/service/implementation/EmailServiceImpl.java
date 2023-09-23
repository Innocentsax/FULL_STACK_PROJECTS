package com.example.hive.service.implementation;

import com.example.hive.dto.request.EmailDto;
import com.example.hive.exceptions.CustomException;
import com.example.hive.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;


@Service
@Slf4j
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

        private final JavaMailSender mailSender;

    @Override
    @Async
    public void sendEmail(EmailDto emailDto) {
        log.info("sending email to {}" , emailDto.getRecipient());
        MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom("hive@blessingchuks.tech","Hive Application");
            mimeMessageHelper.setTo(emailDto.getRecipient());
            mimeMessageHelper.setSubject(emailDto.getSubject());
            mimeMessageHelper.setText(emailDto.getBody(), true);

        };

        try {
            CompletableFuture.runAsync(() ->
                    mailSender.send(mimeMessagePreparator)).exceptionally(exp -> {
                throw new CustomException("Exception occurred sending mail [message]: " + exp.getLocalizedMessage());
            });
            log.info("email has sent!!");
        }catch (MailException exception) {
            log.error("Exception occurred when sending mail {}",exception.getMessage());
            throw new CustomException("Exception occurred when sending mail to " + emailDto.getRecipient(), HttpStatus.EXPECTATION_FAILED);
        }
    }




}
