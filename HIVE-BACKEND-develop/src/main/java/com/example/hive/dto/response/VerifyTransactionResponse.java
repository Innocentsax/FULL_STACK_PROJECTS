package com.example.hive.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.stereotype.Component;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerifyTransactionResponse {

    /**
     * this status is "true" if the request is successful and false if is not
     * NOTE: This does not mean the transaction was successful, status inside data holds that information
     */
    private String status;
    /**
     * information about the request, could be "verification successful" or "invalid key"
     */
    private String message;

    // will be used to fill in the taskdto
    private String paymentLogId;

    private Data data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Component
    @Getter
    @Setter
    public static class Data {
        private String status;
        private String reference;
        private double amount;
        private String paidAt;
        private String createdAt;
        private String channel;
        private String currency;
        private String transactionDate;

    }

}
