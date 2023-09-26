package com.example.money_way.dto.response;

import com.example.money_way.enums.Status;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class TransactionReceipt {

    private Long transactionId;
    private Long userId;
    private BigDecimal amount;
    private String virtualAccountRef;
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String request_id;
    private String responseMessage;
    private String providerStatus;
    private String paymentType;
    private Date createdAt;

}
