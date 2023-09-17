package com.wakacast.dto.search_criteria;

import com.wakacast.enums.Gender;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleMatchSearchCriteria {
    private String projectName;
    private String projectType;
    private String talentSkill;
    private Gender gender;
    private int minAge;
    private int maxAge;
    private String languages;
    private String country;
    private String state;
    private String city;
    private String postalZipCode;
}

