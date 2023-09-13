package com.decagon.adire.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@Getter
@Setter
public class IncomeReportRequestDto {

    private LocalDateTime start;
    private LocalDateTime end;
    private String designerId;
}
