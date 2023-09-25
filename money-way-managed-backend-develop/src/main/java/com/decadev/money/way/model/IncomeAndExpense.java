package com.decadev.money.way.model;


import com.decadev.money.way.enums.TransactionCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IncomeAndExpense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @Enumerated(value = EnumType.STRING)
    private TransactionCategory category;

    @CreationTimestamp
    private LocalDateTime date;

    private int year;

    private int month;

    private int week;

    private String amount;




}
