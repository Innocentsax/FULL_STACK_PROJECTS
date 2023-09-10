package com.hrsupportcentresq014.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobSearchRequest {
    private String keywords;
    private String filter;
    private String department;
    private int page;
    private int size;
}
