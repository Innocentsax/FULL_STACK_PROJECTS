package com.decagon.eventhubbe.events.password;

import com.decagon.eventhubbe.domain.entities.AppUser;
import com.decagon.eventhubbe.domain.entities.ConfirmationToken;
import com.decagon.eventhubbe.service.ConfirmationTokenService;
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
public class ForgotPasswordEventListener implements ApplicationListener<ForgotPasswordEvent> {
    private final ConfirmationTokenService confirmationTokenService;
    private final JavaMailSender javaMailSender;
    @Override
    public void onApplicationEvent(ForgotPasswordEvent event) {
        AppUser appUser = event.getAppUser();
        String tokens = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(tokens, appUser);
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        String url = event.getApplicationUrl() + "/tokens?token=" + tokens;
        try{
            sendConfirmationToken(url,appUser);
        }catch(UnsupportedEncodingException | MessagingException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public void sendConfirmationToken(String url, AppUser appUser) throws MessagingException, UnsupportedEncodingException {
        String subject = "Password Reset Request";
        String senderName = "Event Hub Inc.";
        String mailContent = "<p> Hi, "+appUser.getFirstName()+" </p>"+
                "<p> Welcome to Event Hub Inc. </p>"+
                "<p>Please, follow the link below to reset your password. </p>" +
                "<a href=\""+url+"\"> Click here</a>" +
                "<p> Thank you <br>" + senderName;
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setFrom("resetpassword@eventhub.com",senderName);
        helper.setSubject(subject);
        helper.setTo(appUser.getEmail());
        helper.setText(mailContent,true);
        javaMailSender.send(mimeMessage);
    }
}
