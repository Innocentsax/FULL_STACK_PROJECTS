package com.example.hive.dto.request;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
@Value
@Builder
public class PayStackTransferRequest {
    String source;
    String reason;
    BigDecimal amount;
    String recipient;
    String reference;
}
