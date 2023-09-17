package com.wakacast.dto;

import com.wakacast.enums.Gender;
import com.wakacast.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CastCallDtoAdmin {
    private Long id;
    private String talentSkill;
    private int minAge;
    private int maxAge;
    private Gender gender;
    private UserDto publisher;
    private LocalDate postExpiryDate;
    private String projectName;
    private String projectDescription;
    private String projectType;
    private String languages;
    private String country;
    private String state;
    private String city;
    private String postalZipCode;
    private String otherRequirements;
    private Set<User> applicants;
    private Integer reportedCount;
    private boolean isReportedEnough;
}
