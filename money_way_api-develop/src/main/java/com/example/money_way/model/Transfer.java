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
@Table(name = "transfer_tbl")
public class Transfer extends Base {
    private String email;
    private String bankName;
    private String accountNumber;
    private String description;
    private String referenceId;
    private BigDecimal amount;
    @Column(nullable = false)
    private Long userId;

}
