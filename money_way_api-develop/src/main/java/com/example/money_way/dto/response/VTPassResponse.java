package com.example.money_way.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class VTPassResponse {
    private String transactionId;
    private String status;
    private String productName;
    private String uniqueElement;
    private double unitPrice;
    private int quantity;
    private String type;
    private String email;
    private String phoneNumber;
}
