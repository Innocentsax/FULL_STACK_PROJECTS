package com.wakacast.dto.payment_dtos.verify_transaction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class Authorization {
    @JsonProperty("authorization_code")
    private String authorizationCode;
    private String bin;
    private String last4;
    @JsonProperty("exp_month")
    private String expMonth;
    @JsonProperty("exp_year")
    private String expYear;
    private String channel;
    @JsonProperty("card_type")
    private String cardType;
    private String bank;
    @JsonProperty("country_code")
    private String countryCode;
    private String brand;
    private boolean reusable;
    private String signature;
    @JsonProperty("account_name")
    private String accountName;
}
