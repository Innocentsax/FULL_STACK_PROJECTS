package com.example.money_way.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class DataPurchaseRequest {
    @NotBlank(message = "Please enter the service ID")
    private String serviceID;
    @NotBlank(message = "billers_code cannot be blank")
    private String billersCode;
    @NotBlank(message = "variation_code cannot be blank")
    private String variationCode;
    @NotNull(message = "Please enter the amount")
    private BigDecimal amount;
    @NotBlank(message = "Phone number cannot be blank")
    private String phoneNumber;
    @NotBlank(message = "pin cannot be blank")
    private String pin;
    private boolean saveBeneficiary;
}

