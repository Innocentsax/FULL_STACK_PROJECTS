package com.example.hive.repository;

import com.example.hive.entity.Notification;
import com.example.hive.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, String> {

    List<Notification> findNotificationsByUserOrderByCreatedAtDesc(User user);
}

