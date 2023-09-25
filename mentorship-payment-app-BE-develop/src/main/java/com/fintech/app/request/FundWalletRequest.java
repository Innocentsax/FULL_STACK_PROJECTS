package com.fintech.app.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class FundWalletRequest {

    private String event;

//    @JsonProperty("event.type")
//    private String event_type;
    
    private Data data;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    public static class Data {
        private Long id;

        @JsonProperty("tx_ref")
        private String txRef;
        @JsonProperty("flw_ref")
        private String flwRef;
        private int amount;
        private String currency;
        private String narration;
        private String status;
        @JsonProperty("payment_type")
        private String paymentType;
        private LocalDateTime created_at;
        @JsonProperty("account_id")
        private Long accountId;
        private Customer customer;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    public static class Customer {
        private Long id;
        private String name;
        @JsonProperty("phone_number")
        private String phoneNumber;
        private String email;
        @JsonProperty("created_at")
        private LocalDateTime createdAt;
    }
}
