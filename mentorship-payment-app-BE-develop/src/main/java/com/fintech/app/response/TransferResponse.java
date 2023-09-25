package com.fintech.app.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TransferResponse {

    private Long transferId;
    @JsonProperty("account_bank")
    private String  accountBank;

    @JsonProperty("account_number")
    private String accountNumber;

    private Integer amount;
    private String narration;
    private String currency;
    private String reference;

    private String status;

    private LocalDateTime createdAt;
    private LocalDateTime modifyAt;
}
