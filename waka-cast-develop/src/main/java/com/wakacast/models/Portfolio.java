package com.wakacast.models;

import com.wakacast.enums.PortfolioType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Portfolio extends BaseClass{
    private String portfolioTitle;
    @Enumerated(EnumType.STRING)
    private PortfolioType portfolioType;
    private String portfolioUrl;
}
