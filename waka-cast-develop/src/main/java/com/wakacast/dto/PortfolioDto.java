package com.wakacast.dto;

import com.wakacast.enums.PortfolioType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioDto {
    private String portfolioTitle;
    private PortfolioType portfolioType;
}
