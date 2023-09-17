package com.decagon.safariwebstore.configuration;

import com.decagon.safariwebstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class SchedulerConfiguration {

    private final UserService userService;

    @Autowired
    public SchedulerConfiguration(UserService userService){
        this.userService = userService;
    }

    @Scheduled(fixedRate = 60000L)
    public void scheduleActionWithFixedRate() { userService.deactivateResetPasswordToken(); }
}
