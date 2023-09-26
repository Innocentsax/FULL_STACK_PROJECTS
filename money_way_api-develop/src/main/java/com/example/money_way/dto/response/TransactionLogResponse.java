package com.example.money_way.dto.response;

import com.example.money_way.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionLogResponse {
    private String accountName;
    private String requestId;
    private String currency;
    private BigDecimal amount;
    private String virtualAccountRef;
    private String description;
    private String status;
    private String paymentType;
    private String responseMessage;
    private String providerStatus;
    private Date date;
}

