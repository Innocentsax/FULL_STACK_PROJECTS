package com.example.hive.utils.event.listeners;

import com.example.hive.entity.Task;
import com.example.hive.entity.User;
import com.example.hive.service.EmailService;
import com.example.hive.service.implementation.NotificationServiceImpl;
import com.example.hive.utils.EmailTemplates;
import com.example.hive.utils.event.listeners.TaskCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
@Log4j2
public class TaskCreatedEventListener implements ApplicationListener<TaskCreatedEvent> {

    private final EmailService emailService;
    private final NotificationServiceImpl notificationService;

    @Override
    public void onApplicationEvent(TaskCreatedEvent event) {
        User user = event.getUser();
        Task task = event.getTask();

        try{
            emailService.sendEmail(EmailTemplates.taskCreationNotificationEmail(user, task, event.getApplicationUrl()));
            notificationService.taskCreationNotification(task, user);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
