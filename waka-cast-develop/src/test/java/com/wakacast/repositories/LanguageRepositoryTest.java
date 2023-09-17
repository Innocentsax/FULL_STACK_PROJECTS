package com.wakacast.repositories;

import com.wakacast.models.Language;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class LanguageRepositoryTest {
    private final LanguageRepository languageRepository;
    private Language language;

    @Autowired
    LanguageRepositoryTest(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    @BeforeEach
    void setUp() {
        language = new Language();
        language.setLanguageName("Navajo");
    }

    @Test
    void findLanguageByLanguage() {
        languageRepository.save(language);
        Language language1 = languageRepository.findLanguageByLanguageName("Navajo").get();
        assertThat(language1).isNotNull();
        assertEquals(language1, language);
    }

    @Test
    void save() {
        Language language1 = languageRepository.save(language);
        assertThat(language1).isNotNull();
        assertEquals(language1, language);
    }
}