package com.hrsupportcentresq014.dtos.response;

import com.hrsupportcentresq014.exceptions.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobSearchResponse {
    private List<JobResponseDto> jobResponseDtos;
    private int totalPages;
    private long totalElements;
    private ErrorResponse errorResponse;
}
