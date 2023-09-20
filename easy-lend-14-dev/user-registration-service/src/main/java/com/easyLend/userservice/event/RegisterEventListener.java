package com.easyLend.userservice.event;

import com.easyLend.userservice.domain.entity.AppUser;
import com.easyLend.userservice.domain.entity.VerificationEmail;
import com.easyLend.userservice.services.serviceImpl.VerificationServiceImpl;
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
public class RegisterEventListener implements ApplicationListener<RegisterEvent> {
    private final JavaMailSender javaMailSender;
    private final VerificationServiceImpl confirmationTokenService;
    @Override
    public void onApplicationEvent(RegisterEvent event) {
        AppUser appUser = event.getAppUser();
    String token = UUID.randomUUID().toString();
    VerificationEmail confirmationToken = new VerificationEmail(token, appUser);
        confirmationTokenService.saveToken(confirmationToken);
    String url = event.getUrl()+"/user-auth?token="+token;
        System.out.println(url);
        try{
        sendConfirmationToken(url,appUser);
    }
        catch(UnsupportedEncodingException | MessagingException e){
        throw new RuntimeException(e.getMessage());
    }
}

    public void sendConfirmationToken(String url, AppUser appUser) throws MessagingException, UnsupportedEncodingException {
        String subject = "Email Verification";
        String senderName = "Easy Lend.";
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setFrom("registration@easylend.com",senderName);
        helper.setSubject(subject);
        helper.setTo(appUser.getEmail());
        String mailContent = "<p> Hi, "+appUser.getUsername()+" </p>"+
                "<p> Welcome to Easy Lend. </p>"+
                "<p>Thank you for registering with us, "+""+
                "Please, follow the link below to complete your registration. </p>" +
                "<a href=\""+url+"\"> Verify your email to activate your account</a>" +
                "<p> Thank you <br>" + senderName;

        helper.setText(mailContent,true);
        javaMailSender.send(mimeMessage);
        System.out.println("sent");
    }



}
