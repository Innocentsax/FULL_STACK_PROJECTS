package com.wakacast.dto.response_dtos;

import com.wakacast.enums.PortfolioType;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class PortfolioResponseDto {
    private String portfolioTitle;
    private PortfolioType portfolioType;
    private String portfolioUrl;
}
