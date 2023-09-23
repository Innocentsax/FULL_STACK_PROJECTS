package com.example.food.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal walletBalance;
    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date modifiedAt;
    @OneToOne(mappedBy = "wallet")
    private Users user;
    @OneToMany(mappedBy = "wallet")
    private List<BankDetails> bankDetails;

    @OneToMany(mappedBy = "wallet")
    @ToString.Exclude
    private List<WalletTransaction> walletTransactions;
}
