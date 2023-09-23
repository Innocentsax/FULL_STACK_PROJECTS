package com.example.hive.service;

import com.example.hive.dto.response.NotificationResponseDto;
import com.example.hive.entity.Notification;
import com.example.hive.entity.Task;
import com.example.hive.entity.User;

import java.util.List;

public interface NotificationService {

    NotificationResponseDto taskCreationNotification(Task task, User user);
    NotificationResponseDto taskAcceptanceNotification(Task task, User user);
    NotificationResponseDto doerAcceptanceNotification(Task task, User user);

    List<NotificationResponseDto> getAllNotificationOfUser(String email);
}
