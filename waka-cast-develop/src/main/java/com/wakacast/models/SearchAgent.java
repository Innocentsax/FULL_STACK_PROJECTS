package com.wakacast.models;

import com.wakacast.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SearchAgent extends BaseClass{
    private String projectName;
    private String projectType;
    private String talentSkill;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private int minAge;
    private int maxAge;
    private String location;
    private String languages;
    @OneToOne
    private User user;
}
