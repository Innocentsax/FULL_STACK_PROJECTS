package com.decagon.dev.paybuddy.dtos.responses.vtpass.response.data;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BuyAirtimeResponse {
    private Integer code;
    private String response_description;
    private String requestId;
    private String transactionId;
    private BigDecimal amount;
    private TransactionDate transaction_date;
    private String purchase_code;


}
