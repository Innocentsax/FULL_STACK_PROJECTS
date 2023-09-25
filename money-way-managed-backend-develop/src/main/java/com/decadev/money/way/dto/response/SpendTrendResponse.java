package com.decadev.money.way.dto.response;

import com.decadev.money.way.dto.transaction.AmountPerWeek;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpendTrendResponse {

    private String trendType;

    private Map<Integer, Set<AmountPerWeek>> monthData;
}
