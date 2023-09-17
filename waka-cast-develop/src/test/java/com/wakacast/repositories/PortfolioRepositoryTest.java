package com.wakacast.repositories;

import com.wakacast.enums.PortfolioType;
import com.wakacast.models.Portfolio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PortfolioRepositoryTest {
    private final PortfolioRepository portfolioRepository;
    private Portfolio newPortfolio;

    @Autowired
    public PortfolioRepositoryTest(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }

    @BeforeEach
    void setUp() {
        newPortfolio = new Portfolio();
        newPortfolio.setPortfolioUrl("url");
        newPortfolio.setPortfolioType(PortfolioType.AUDIO);
        newPortfolio.setPortfolioTitle("Trailer");
    }

    @Test
    void savePortfolio() {
        Portfolio savedPortfolio = portfolioRepository.save(newPortfolio);
        assertThat(savedPortfolio).isNotNull();
        assertThat(savedPortfolio.getPortfolioTitle()).isEqualTo("Trailer");
        assertThat(savedPortfolio).isEqualTo(newPortfolio);
    }
}