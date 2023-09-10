package com.goCash.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlutterWaveData {
    @JsonProperty(value = "response_code")
    private String responseCode;
    @JsonProperty(value = "response_message")
    private String responseMessage;
    @JsonProperty(value = "flw_ref")
    private String flwRef;
    @JsonProperty(value = "order_ref")
    private String orderRef;
    @JsonProperty(value = "account_number")
    private String accountNumber;
    @JsonProperty(value = "account_status")
    private String accountStatus;
    private int frequency;
    @JsonProperty(value = "bank_name")
    private String bankName;
    @JsonProperty(value = "created_at")
    private Date createdAt;
    @JsonProperty(value = "expiry_date")
    private String expiryDate;
    private String note;
    private String amount;
}
