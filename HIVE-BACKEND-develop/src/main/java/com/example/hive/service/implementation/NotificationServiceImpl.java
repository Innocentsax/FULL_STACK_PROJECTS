package com.example.hive.service.implementation;

import com.example.hive.dto.response.NotificationResponseDto;
import com.example.hive.entity.Notification;
import com.example.hive.entity.Task;
import com.example.hive.entity.User;
import com.example.hive.enums.Role;
import com.example.hive.exceptions.CustomException;
import com.example.hive.repository.NotificationRepository;
import com.example.hive.repository.UserRepository;
import com.example.hive.service.NotificationService;
import com.example.hive.utils.EpochTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class NotificationServiceImpl implements NotificationService {

    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;

    public NotificationResponseDto taskCreationNotification(Task task, User user) {
        log.info("Sending Notification for task creation {}", user.getEmail());
        UUID userId = task.getTasker().getUser_id();
        User tasker = userRepository.findById(userId).orElseThrow(() ->
        {
            throw new CustomException("User not found");
        });
        if(!tasker.getRole().equals(Role.TASKER)) {
            throw new CustomException("User is not a Tasker");
        }

        Notification notification = Notification.builder()
                .user(tasker)
                .title("Task Created " + "-> " + task.getJobType())
                .body("Your task has been successfully created, kindly await an acceptance")
                .createdAt(LocalDateTime.now().toString())
                .build();
        
        Notification savedNotification = notificationRepository.save(notification);
        return mapToNotificationResponse(savedNotification);
    }

    public NotificationResponseDto taskAcceptanceNotification(Task task, User user) {
        log.info("Sending Notification for task acceptance {}", user.getEmail());
        UUID userId = task.getDoer().getUser_id();
        User doer = userRepository.findById(userId).orElseThrow(() ->
        {
            throw new CustomException("User not found");
        });
        if(!doer.getRole().equals(Role.DOER)) {
            throw new CustomException("User is not a Doer");
        }
        Notification notification = Notification.builder()
                .user(doer)
                .title("Task Acceptance!!!")
                .createdAt(LocalDateTime.now().toString())
                .body("Congratulations! You have successfully accepted a task with the following details: \n"
                        + "Task type: " + task.getJobType() + "\n"
                        + "Task description: " + task.getTaskDescription() + "\n"
                        + "Tasker Service Address: " + task.getTaskDeliveryAddress() + "\n"
                        + "Budget Rate: " + task.getBudgetRate() + "\n"
                        + "Tasker: " + task.getTasker().getFullName() + "\n"
                        + "Thank you for using Hive!")
                .build();

        Notification savedNotification = notificationRepository.save(notification);
        return mapToNotificationResponse(savedNotification);
    }

    public NotificationResponseDto doerAcceptanceNotification(Task task, User user) {
        log.info("Sending Notification to user Tasker {} after doer accepts the task", task.getTasker().getFullName());
        UUID userId = task.getTasker().getUser_id();
        User tasker = userRepository.findById(userId).orElseThrow(() ->
        {
            throw new CustomException("User not found");
        });
        if(!tasker.getRole().equals(Role.DOER)) {
            throw new CustomException("User is not a Doer");
        }
        Notification notification = Notification.builder()
                .user(tasker)
                .title("Task Accepted!")
                .createdAt(LocalDateTime.now().toString())
                .body("Congratulations! Your task has been successfully accepted by " + task.getDoer().getFullName() + "\n"
                        + "Task Details: \n"
                        + "Task type: " + task.getJobType() + "\n"
                        + "Tasker Service Address: " + task.getTaskDeliveryAddress() + "\n"
                        + "Budget Rate: " + task.getBudgetRate() + "\n"
                        + "Thank you for using Hive!")
                .build();

        Notification savedNotification = notificationRepository.save(notification);
        return mapToNotificationResponse(savedNotification);
    }


    public NotificationResponseDto walletFundingNotification(User user, BigDecimal amount) {
        log.info("Sending Wallet Funding Notification to user  {} ", user.getFullName());

        Notification notification = Notification.builder()
                .user(user)
                .title("Wallet Funded!")
                .createdAt(LocalDateTime.now().toString())
                .body("Congratulations! Your wallet has been successfully funded with " + amount + "\n"
                        + "Thank you for using Hive!")
                .build();

        Notification savedNotification = notificationRepository.save(notification);
        return mapToNotificationResponse(savedNotification);
    }

    @Override
    public List<NotificationResponseDto> getAllNotificationOfUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> {
            throw new CustomException("User with the email: " + email + " was not found");
        });
        List<Notification> notifications = notificationRepository.findNotificationsByUserOrderByCreatedAtDesc(user);
        List<NotificationResponseDto> notificationResponseDtos = new ArrayList<>();

        for(Notification notification : notifications) {
            NotificationResponseDto notificationResponseDto = new ModelMapper().map(notification, NotificationResponseDto.class);
            notificationResponseDto.setUserId(user.getUser_id().toString());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
            LocalDateTime datetime = LocalDateTime.parse(notification.getCreatedAt(), formatter);


            notificationResponseDto.setElapsedTime(EpochTime.getElapsedTime(datetime));
            notificationResponseDtos.add(notificationResponseDto);
        }
        return notificationResponseDtos;
    }

    private NotificationResponseDto mapToNotificationResponse(Notification notification) {
        return NotificationResponseDto.builder()
                .title(notification.getTitle())
                .body(notification.getBody())
                .createdAt(notification.getCreatedAt())
                .build();
    }
}
