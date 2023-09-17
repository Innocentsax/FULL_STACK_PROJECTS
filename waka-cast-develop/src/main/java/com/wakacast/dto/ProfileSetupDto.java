package com.wakacast.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.wakacast.enums.Gender;
import com.wakacast.enums.Race;
import com.wakacast.enums.SkinTone;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class ProfileSetupDto {
    @NotNull
    @NotBlank
    private String firstName;
    private String middleName;
    @NotNull
    @NotBlank
    private String surname;
    private String title;
    private String displayName;
    private Gender gender;
    private Set<String> roles;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    @NotNull
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dateOfBirth;
    private Set<String> userPersonas;
    private String phoneNumber;
    private String workPhoneNumber;
    private String headline;
    private String status;
    private String companyName;
    private String country;
    private Set<String> genres;
    private Set<SpokenLanguageDto> spokenLanguages;
    private String height;
    private String weight;
    private SkinTone skinTone;
    private Race race;
    private String education;
    private String ethnicity;
}
