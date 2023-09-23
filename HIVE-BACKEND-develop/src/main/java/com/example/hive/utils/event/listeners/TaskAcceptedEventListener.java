package com.example.hive.utils.event.listeners;

import com.example.hive.entity.Task;
import com.example.hive.entity.User;
import com.example.hive.exceptions.CustomException;
import com.example.hive.service.NotificationService;
import com.example.hive.service.implementation.NotificationServiceImpl;
import com.example.hive.utils.event.TaskAcceptedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationListener;

import java.io.IOException;

@RequiredArgsConstructor
@Log4j2
public class TaskAcceptedEventListener implements ApplicationListener<TaskAcceptedEvent> {

    private final NotificationServiceImpl notificationService;

    @Override
    public void onApplicationEvent(TaskAcceptedEvent event) {
        User user = event.getUser();
        Task task = event.getTask();

        try {
            notificationService.taskAcceptanceNotification(task, user);
            notificationService.doerAcceptanceNotification(task, user);

        } catch (CustomException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
