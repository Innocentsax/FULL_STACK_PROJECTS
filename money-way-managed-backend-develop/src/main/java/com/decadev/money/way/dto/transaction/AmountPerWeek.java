package com.decadev.money.way.dto.transaction;

import lombok.*;

import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AmountPerWeek implements Comparable<AmountPerWeek>{


    private int week;
    private String amount;


    @Override
    public int compareTo(AmountPerWeek o) {
        return Integer.compare(this.week, o.getWeek());
    }
}
