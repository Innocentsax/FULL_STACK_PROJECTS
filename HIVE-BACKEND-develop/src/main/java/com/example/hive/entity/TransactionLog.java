package com.example.hive.entity;


import com.example.hive.constant.TransactionStatus;
import com.example.hive.constant.TransactionType;
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
@Table(name = "transactions")
public class TransactionLog extends AuditEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String transactionId;

    @Column(name = "amount")
    private BigDecimal amount;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "transaction_type", length = 20)
    private TransactionType transactionType;


    @Column(name = "transaction_date")
    private String transactionDate;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "transaction_status", length = 50)
    private TransactionStatus transactionStatus;

    @ManyToOne(cascade = CascadeType.MERGE)
    private User user;


}
