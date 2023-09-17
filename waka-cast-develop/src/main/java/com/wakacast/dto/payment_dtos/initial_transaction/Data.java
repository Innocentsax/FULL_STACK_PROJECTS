package com.wakacast.dto.payment_dtos.initial_transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Data {
    @JsonProperty("authorization_url")
    private String authorizationUrl;
    @JsonProperty("access_code")
    private String accessCode;
    private String reference;
}
