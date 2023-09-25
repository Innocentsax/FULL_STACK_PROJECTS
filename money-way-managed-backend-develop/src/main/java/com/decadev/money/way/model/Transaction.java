package com.decadev.money.way.model;


import com.decadev.money.way.enums.TransactionCategory;

import com.decadev.money.way.enums.Status;

import com.decadev.money.way.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@ToString(exclude = "user")

public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String amount;

    private String cashTransferType;

    @CreationTimestamp
    private LocalDateTime date;

    @Enumerated(value = EnumType.STRING)
    private TransactionCategory category;

    @Enumerated(value = EnumType.STRING)
    private TransactionType transactionType;

    private String beneficiary;

    private String sender;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String referenceId;

    @Enumerated(EnumType.STRING)
    private Status status;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private User user;


}
