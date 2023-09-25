package com.decadev.money.way.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FLWData {
    @JsonProperty("response_code")
    private String response_code;

    @JsonProperty("response_message")
    private String response_message;

    @JsonProperty("flw_ref")
    private String flw_ref;

    @JsonProperty("order_ref")
    private String order_ref;

    @JsonProperty("account_number")
    private String account_number;

    @JsonProperty("frequency")
    private String frequency;

    @JsonProperty("bank_name")
    private String bank_name;

    @JsonProperty("created_at")
    private String created_at;

    @JsonProperty("expiry_date")
    private String expiry_date;

    @JsonProperty("note")
    private String note;

    @JsonProperty("amount")
    private String amount;

}
