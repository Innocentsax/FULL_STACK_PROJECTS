package com.goCash.dto.request;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocalTransferRequest {
    private String accountNumber;
    private double amount;
    private String narration;
}
