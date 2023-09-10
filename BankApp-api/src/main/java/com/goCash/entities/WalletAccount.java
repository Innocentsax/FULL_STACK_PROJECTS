package com.goCash.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor

@Data
@Builder
@Entity
@Table(name = "wallet")

public class WalletAccount extends BaseEntity{

    private String accountNumber;
    private double balance;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
