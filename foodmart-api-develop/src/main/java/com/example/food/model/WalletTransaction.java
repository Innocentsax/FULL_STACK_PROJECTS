package com.example.food.model;

import com.example.food.Enum.TransactionType;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Date;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "wallet_transaction")
@Entity
public class WalletTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false,unique = true)
    private  Long WalletTransactionId;

    @NotNull(message = "amount field is empty")
    private BigDecimal amount;
    @CreationTimestamp
    private Date transactionDate;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    private String transactionReference;

    @ManyToOne(cascade = CascadeType.ALL)
    private Wallet wallet;

}
