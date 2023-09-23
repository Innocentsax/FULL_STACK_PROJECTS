package com.example.hive.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String wallet_id;
    @Column(name = "wallet_balance")
    private BigDecimal accountBalance ;
    @Column(name = "activated")
    private boolean isActivated = false;
    @OneToOne
    private User user;
}
