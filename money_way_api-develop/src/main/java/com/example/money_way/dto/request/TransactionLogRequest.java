package com.example.money_way.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionLogRequest {

    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Invalid Start Date")
    private String startDate;

    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Invalid End Date")
    private String endDate;

    private Integer pageSize = 5;

    private Integer pageNo;
}
