package com.goCash.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AirtimePurchaseRequest {

    @JsonProperty("request_id")
    String request_id;
    @JsonProperty("serviceID")
    String serviceID;
    @JsonProperty("amount")
    double amount;
    @JsonProperty("phone")
    String phone;
}
