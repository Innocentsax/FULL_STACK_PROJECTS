package com.hrsupportcentresq014.dtos.response;

import lombok.*;

import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor

public class JobResponseDto {
    private String title;
    private String description;
    private String departmentName;
    private String createdOn;
    private String closingDate;
    private Boolean isActive;

}
