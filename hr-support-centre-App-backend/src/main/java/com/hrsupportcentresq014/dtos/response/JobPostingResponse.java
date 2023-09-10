package com.hrsupportcentresq014.dtos.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobPostingResponse {
    private String title;
    private String departmentName;
    private String closingDate;
    private String description;
    private String requirements;
    private boolean isActive;
}
