package com.wakacast.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wakacast.enums.Gender;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CastCall extends BaseClass{

    private String talentSkill;
    private int minAge;
    private int maxAge;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @JsonIgnore
    @ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    private User publisher;
    private LocalDate postExpiryDate;
    private String projectName;
    private String projectDescription;
    private String projectType;
    private String languages;
    private String otherRequirements;
    private String country;
    private String state;
    private String city;
    private String postalZipCode;
    @ManyToMany
    private Set<User> applicants;
    private Integer reportedCount = 0;
    private boolean isReportedEnough = false;

}
