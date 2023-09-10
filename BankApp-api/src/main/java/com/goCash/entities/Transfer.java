package com.goCash.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Transfers")
public class Transfer extends BaseEntity{
    private String sourceAccountNumber;
    private String destinationAccountNumber;
    private BigDecimal amount;
}
