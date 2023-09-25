package com.fintech.app.util;

import com.fintech.app.model.User;
import com.fintech.app.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;
@RequiredArgsConstructor
@Slf4j
@Component
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    private final UserService userService;
    private final JavaMailSender mailSender;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        // create verification token
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.saveVerificationTokenForUser(token, user);

        // send email to user
        String url = event.getApplicationUrl()
                    + "/verifyRegistration?token="
                    + token;
        sendVerificationEmail(user,url);
        log.info("click the link {} to verify your account: ", url);
    }

    private void sendVerificationEmail(User user, String url) {
        String subject = "Please verify your registration";
        String senderName = "Fintech App";
        String mailContent = "<p> Dear "+ user.getLastName() +", </p>";
        mailContent += "<p> Please click the link below to verify your registration, </p>";
        mailContent += "<h3><a href=\""+ url + "\"> VERIFY </a></h3>";
        mailContent += "<p>Thank you <br/> Fintech team </p>";
        Util.setMailCredentials(user, subject, senderName, mailContent, mailSender);

    }
}
