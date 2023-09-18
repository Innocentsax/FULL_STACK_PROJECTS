package com.example.decapay.configurations.mails;

import com.example.decapay.pojos.mailDto.MailDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
@AllArgsConstructor
@Service
public class EmailSenderServiceImpl implements EmailSenderService {

    private final JavaMailSender mailSender;

    @Override
    public ResponseEntity<String> sendEmail(MailDto mailDto) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("decapay1@gmail.com");
        simpleMailMessage.setTo(mailDto.getTo());
        simpleMailMessage.setSubject(mailDto.getSubject());
        simpleMailMessage.setText(mailDto.getMessage());

        mailSender.send(simpleMailMessage);

        return new ResponseEntity<>("Message sent successfully", HttpStatus.OK);
    }


    @Override
    public void sendPasswordResetEmail(String email, String subject, String link) {
        subject = "Reset Password";
        String body = "Kindly use the link below to reset your password  " + link;

        MailDto mailServiceDto = new MailDto(email, subject, body);
        sendEmail(mailServiceDto);

    }

}
