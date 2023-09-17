package com.wakacast.models;

import com.wakacast.enums.LanguageProficiency;
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
public class SpokenLanguage extends BaseClass {
    private String language;
    @Enumerated(EnumType.STRING)
    private LanguageProficiency proficiency;
}
