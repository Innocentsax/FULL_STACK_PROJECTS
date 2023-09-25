package com.decadev.money.way.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
public class TransactionHistoryRequest {
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Must be YYYY-MM-DD")
    private String startDate;

    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Must be YYYY-MM-DD")
    private String endDate;

    private Integer pageSize;

    private Integer pageNo;
    String sortBy;
    String sortDir;

    private BigDecimal maxAmount;
    private BigDecimal minAmount;
}
