package com.example.hive.dto.response;

import com.example.hive.entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponseDto {

    private String userId;

    private String title;

    private String body;

    private String createdAt;

    private String elapsedTime;

}
