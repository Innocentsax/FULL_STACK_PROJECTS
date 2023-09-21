package com.decagon.eventhubbe.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketsSalesResponse {
    private Integer quantitySold;
    private BigDecimal amount;
    private EventTicketResponse eventTicketResponse;
}
