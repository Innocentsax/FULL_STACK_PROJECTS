package com.fintech.app.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionHistoryDto {


    private Long id;
    private String name;
    private String bank;
    private String transactionTime;
    private String type;
    private String amount;

}
