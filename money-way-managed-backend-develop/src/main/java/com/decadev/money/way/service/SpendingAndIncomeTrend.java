package com.decadev.money.way.service;

import com.decadev.money.way.dto.response.SpendTrendResponse;

import java.math.BigDecimal;

public interface SpendingAndIncomeTrend {

    void setIncomeTrend(BigDecimal amount);

    void setSpendingTrend(BigDecimal amount);

    SpendTrendResponse getSpendingTrend();
}
