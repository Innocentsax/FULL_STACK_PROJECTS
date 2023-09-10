package com.goCash.entities;

import com.goCash.enums.TransactionStatus;
import com.goCash.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.context.annotation.Bean;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transactions")
public class Transaction extends BaseEntity {


    @Column(name = "reference")
    private String reference;

    @Column(name = "provider_reference")
    private String providerReference;

    @Column(name = "provider_message")
    private String providerMessage;

    @Column(name = "message")
    private String message;

    @Column(name = "amount")
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @CreationTimestamp
    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "status")
    private TransactionStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}