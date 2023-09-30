package com.decagon.dev.paybuddy.models;

import com.decagon.dev.paybuddy.enums.TransactionStatus;
import com.decagon.dev.paybuddy.enums.TransactionType;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
@Entity
@Builder
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Table(name = "transaction_tbl")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false,unique = true)
    private Long transactionId;
    private String referenceNumber;
    private BigDecimal amount;
    private String name;
    private String bankCode;
    private String transactionReference;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    @CreationTimestamp
    private LocalDate dateOfTransaction;
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;
    private String description;
    @ManyToOne
    @JoinColumn(name = "wallet_wallet_id")
    private Wallet wallet;


}
