package com.fintech.app.response;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WalletResponse {
    Long walletId;
    String accountNumber;
    String bankName;
    String balance;
    String createdAt;
    String updatedAt;
}
