package com.example.hive.entity;

import com.example.hive.constant.TransactionStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
public class PaymentLog extends AuditEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String paymentLogId;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "transaction_reference")
    private String transactionReference;

    @Column(name = "transaction_date")
    private String transactionDate;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "transaction_status", length = 50)
    @Builder.Default
    private TransactionStatus transactionStatus = TransactionStatus.PENDING;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private User taskerDepositor;

}
