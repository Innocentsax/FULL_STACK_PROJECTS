package com.example.money_way.dto.webhook;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class VerifyTransaction {
    private Long id;
    private String status;
    private String tx_ref;
    private BigDecimal amount;
    private String currency;
    private String charged_amount;
    private String processor_response;
    private String auth_model;
    private String payment_type;
    private String narration;

}
