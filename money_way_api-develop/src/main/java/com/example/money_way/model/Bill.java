package com.example.money_way.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "bill_tbl")
public class Bill extends Base{
    private String service;
    private String provider;
    private String billPackage;
    private String recipientAccount;
    private BigDecimal amount;
    @Column(nullable = false)
    private Long userId;
}
