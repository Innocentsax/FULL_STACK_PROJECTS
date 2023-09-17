package com.wakacast.dto;

import com.wakacast.dto.response_dtos.PortfolioResponseDto;
import com.wakacast.dto.response_dtos.UserPersonaDto;
import com.wakacast.enums.Gender;
import com.wakacast.enums.Race;
import com.wakacast.enums.SkinTone;
import com.wakacast.enums.UserType;
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
public class UserDto {
    private Long id;
    private String email;
    private String firstName;
    private String middleName;
    private String surname;
    private String title;
    private String displayName;

    private Gender gender;

    private Set<RoleDto> roles;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private LocalDate dateOfBirth;
    private String profilePictureUrl;

    private Set<UserPersonaDto> userPersonas;
    private String phoneNumber;
    private String workPhoneNumber;
    private String headline;
    private String status;

    private UserType userType;
    private boolean isAccountVerified;
    private String companyName;
    private String country;

    private Set<PortfolioResponseDto> portfolios;

    private Set<GenreDto> genres;

    private Set<SpokenLanguageDto> spokenLanguages;
    private String height;
    private String weight;

    private SkinTone skinTone;

    private Race race;
    private String education;
    private String ethnicity;
}
