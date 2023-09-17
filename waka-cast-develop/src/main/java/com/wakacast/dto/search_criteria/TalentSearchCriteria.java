package com.wakacast.dto.search_criteria;

import com.wakacast.enums.Gender;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TalentSearchCriteria {
    private String keyword; //talent-Set
    private String allAuditionsAndJob; //Genre-Set
    private String state;
    private Gender gender;
}

