package com.decadev.money.way.model;

import com.decadev.money.way.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Beneficiary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phoneNumber;

    private TransactionType transactionType;

    private String cashTransferType;

    private String bank;

    private String name;

    private String accountNumber;

    @ManyToOne
    private User user;
}
