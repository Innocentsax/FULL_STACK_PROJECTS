package com.wakacast.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ReportCastCallDto {
    private Long id;
    @CreationTimestamp
    private LocalDateTime createDate;
    private String reasonForReporting;
    private String otherReasonForReporting;
    private String projectName;
    private String projectType;
    private String reporterName;
}
