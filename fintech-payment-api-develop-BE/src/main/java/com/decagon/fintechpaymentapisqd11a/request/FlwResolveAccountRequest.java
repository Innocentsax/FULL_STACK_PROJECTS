package com.decagon.fintechpaymentapisqd11a.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlwResolveAccountRequest {
    @JsonProperty("account_number")
    private String accountNumber;

    @JsonProperty("account_bank")
    private String accountBank;
}
