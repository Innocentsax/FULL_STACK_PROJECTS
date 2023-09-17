package com.wakacast.repositories;

import com.wakacast.enums.LanguageProficiency;
import com.wakacast.models.SpokenLanguage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class SpokenLanguageRepositoryTest {
    private final SpokenLanguageRepository languageRepository;

    private SpokenLanguage spokenLanguage;

    @Autowired
    public SpokenLanguageRepositoryTest(SpokenLanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    @BeforeEach
    void setUp() {
        spokenLanguage = new SpokenLanguage();
        spokenLanguage.setLanguage("English");
        spokenLanguage.setProficiency(LanguageProficiency.ADVANCED);
    }

    @Test
    void saveLanguage() {
        SpokenLanguage savedLanguage = languageRepository.save(spokenLanguage);
        assertEquals(savedLanguage, spokenLanguage);
    }
}