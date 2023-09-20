package com.easyLend.userservice.event;


import com.easyLend.userservice.domain.entity.AppUser;
import com.easyLend.userservice.domain.entity.VerificationEmail;
import com.easyLend.userservice.services.VerificationEmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ForgotPasswordEventListener implements ApplicationListener<PasswordEvent> {
    private final VerificationEmailService confirmationTokenService;
    private final JavaMailSender javaMailSender;
    @Override
    public void onApplicationEvent(PasswordEvent event) {
        AppUser appUser = event.getAppUser();
        String tokens = UUID.randomUUID().toString();
        VerificationEmail confirmationToken = new VerificationEmail(tokens, appUser);
        confirmationTokenService.saveToken(confirmationToken);
        String url = event.getUrl() + "/tokens?token=" + tokens;
        try{
            sendConfirmationToken(url,appUser);
        }catch(UnsupportedEncodingException | MessagingException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public void sendConfirmationToken(String url, AppUser appUser) throws MessagingException, UnsupportedEncodingException {
        String subject = "Password Reset Request";
        String senderName = "Easy Lend .";
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setFrom("resetpassword@easylend.com",senderName);
        helper.setSubject(subject);
        helper.setTo(appUser.getEmail());
        String mailContent = "<p> Hi, "+appUser.getUsername()+" </p>"+
                "<p> Welcome to Easy Lend . </p>"+
                "<p>Please, follow the link below to reset your password. </p>" +
                "<a href=\""+url+"\"> Click here</a>" +
                "<p> Thank you <br>" + senderName;
        helper.setText(mailContent,true);
        javaMailSender.send(mimeMessage);
    }
}