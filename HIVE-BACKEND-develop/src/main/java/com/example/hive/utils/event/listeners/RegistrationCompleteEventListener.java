package com.example.hive.utils.event.listeners;

import com.example.hive.entity.User;
import com.example.hive.service.EmailService;
import com.example.hive.service.UserService;
import com.example.hive.utils.EmailTemplates;
import com.example.hive.utils.event.RegistrationCompleteEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    private final EmailService emailService;
    private final UserService userService;




    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        User user = event.getUser();

        try {
            // create and save verification token for user
            String token = userService.generateVerificationToken(user);
            emailService.sendEmail(EmailTemplates.createVerificationEmail(user, token, event.getApplicationUrl()));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }



}

