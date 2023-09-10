package com.goCash.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class AccountVerificationRequest {
    @JsonProperty("account_number")
    private String accountNumber;

    @JsonProperty("account_bank")
    private String accountBank;
}
