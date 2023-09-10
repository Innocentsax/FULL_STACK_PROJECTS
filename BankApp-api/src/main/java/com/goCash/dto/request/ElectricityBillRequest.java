package com.goCash.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ElectricityBillRequest {

    @JsonProperty("request_id")
    private String requestId;

    private String serviceID;

    private String billersCode;

    @JsonProperty("variation_code")
    private String variationCode;

    private double amount;

    private String phone;

}
