package com.example.hive.dto.response;

import com.example.hive.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskResponseDto {

    private String jobType;
    @Size(max = 250)
    private String taskDescription;
    private BigDecimal budgetRate;
    private String taskAddress;
    private String taskDeliveryAddress;
    private Integer estimatedTime;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private String taskDuration;
    private Status status;
    private String taskId;
}
