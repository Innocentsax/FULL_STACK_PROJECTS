package com.decagon.chompapp.services.Impl;

import com.decagon.chompapp.controllers.RegistrationController;
import com.decagon.chompapp.models.User;
import com.decagon.chompapp.repositories.UserRepository;
import com.decagon.chompapp.dtos.EmailSenderDto;
import com.decagon.chompapp.services.EmailSenderService;
import com.decagon.chompapp.utils.Utility;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

@Service
@Async
@AllArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService {

    private final static Logger LOGGER = LoggerFactory.getLogger(EmailSenderService.class);

    private final JavaMailSender mailSender;

    @Override
    @Async
    public ResponseEntity<String> send(EmailSenderDto emailSenderDto) {
        try{
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setTo(emailSenderDto.getTo());
            helper.setSubject(emailSenderDto.getSubject());
            helper.setText(emailSenderDto.getContent(), true);
            mailSender.send(mimeMessage);
            return  new ResponseEntity<>("Message sent successfully", HttpStatus.OK);
        } catch (MessagingException e){

            LOGGER.error("failed to send email", e);
            throw new IllegalStateException("failed to send email");
        }
    }

    @Override
    public void sendRegistrationEmail(String email, String token) throws MalformedURLException {
        URL url = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(RegistrationController.class).confirmRegistration(token)
        ).toUri().toURL();

        String content = "Hello,\n"
                + "Congrats!!! Your registration was successful.\n"
                + "Click the link below to verify your account:\n"
                + url
//                + "\">Verify my account</a>\n"
                + "\n Ignore this email if you did not register, "
                + "or you have not made the request.";

        LOGGER.info(content);
        send(new EmailSenderDto(email, "Chomp: Confirm Registration", content));
    }

}
