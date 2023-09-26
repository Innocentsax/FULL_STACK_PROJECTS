package com.example.money_way.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "wallet_tbl")
public class Wallet extends Base {
    private String bankName;
    private String accountNumber;
    private BigDecimal balance;
    private String virtualAccountRef;
    @Column(nullable = false)
    private Long userId;
}
