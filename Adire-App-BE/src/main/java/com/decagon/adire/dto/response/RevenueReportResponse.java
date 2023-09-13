package com.decagon.adire.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RevenueReportResponse {

    private Double totalRevenue;
    private Integer numberOfOrders;
}
