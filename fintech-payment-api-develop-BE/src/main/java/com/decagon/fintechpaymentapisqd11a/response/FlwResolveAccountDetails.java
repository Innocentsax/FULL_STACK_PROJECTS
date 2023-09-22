package com.decagon.fintechpaymentapisqd11a.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlwResolveAccountDetails {
    private String status;
    private String message;
    private Data data;
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Data{
        @JsonProperty("account_number")
        private String accountNumber;
        @JsonProperty("account_name")
        private String accountName;
    }

}
