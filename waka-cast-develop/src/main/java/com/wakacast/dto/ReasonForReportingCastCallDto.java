package com.wakacast.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReasonForReportingCastCallDto {
    private String content;
    private String otherReasons;
}
