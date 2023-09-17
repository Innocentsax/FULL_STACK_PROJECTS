package com.wakacast.repositories;

import com.wakacast.models.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface LanguageRepository extends JpaRepository<Language, Long> {
    Optional<Language> findLanguageByLanguageName(String language);
}
