package com.wakacast.dto;

import com.wakacast.enums.LanguageProficiency;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpokenLanguageDto {
    private String language;
    private LanguageProficiency proficiency;
}
