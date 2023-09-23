package com.example.hive.dto.request;

import com.example.hive.enums.Status;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDto {
    private String jobType;
    @Size(max = 250)
    private String taskDescription;
    private BigDecimal budgetRate;
    private String taskAddress;
    private String taskDeliveryAddress;
    private Integer estimatedTime;
    private String taskDuration;
}
