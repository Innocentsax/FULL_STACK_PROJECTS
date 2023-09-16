package com.decagon.chompapp.models;

import com.decagon.chompapp.enums.TransactionType;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
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

    @DateTimeFormat(pattern = "dd/mm/yyyy")
    @NotNull (message = "transaction date field is empty")
    private Date transactionDate;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @NotNull(message = "amount field is empty")
    private double amount;

    @ManyToOne(cascade = CascadeType.ALL)
    private Wallet wallet;

}
