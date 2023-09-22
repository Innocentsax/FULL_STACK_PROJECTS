package com.decagon.fintechpaymentapisqd11a.dto;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocalBankTransferDto {

    @Column(length = 10, nullable = false)
    private String accountNumber;
    @NotNull(message = "Bank name is required")
    private String bankName;
    @NotNull(message = "Amount is required")
    private BigDecimal amount;
    @NotNull(message = "Narration is required")
    private String narration;
    @NotNull(message = "Pin is required")
    private String pin;
    @CreationTimestamp
    private LocalDateTime transactionDate;
}
