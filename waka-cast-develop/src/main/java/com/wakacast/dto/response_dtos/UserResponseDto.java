package com.wakacast.dto.response_dtos;

import com.wakacast.enums.Gender;
import com.wakacast.enums.Race;
import com.wakacast.enums.SkinTone;
import com.wakacast.enums.UserType;
import com.wakacast.models.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseDto {
    private String email;
    private String firstName;
    private String middleName;
    private String surname;
    private String title;
    private String displayName;
    private Gender gender;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String profilePictureUrl;
    private Set<UserPersona> userPersonas;
    private String phoneNumber;
    private String status;
    private UserType userType;
    private boolean isAccountVerified;
    private boolean isActive;
    private String companyName;
    private Set<Genre> genres;
    private Set<Role> roles;
    private Set<SpokenLanguage> spokenLanguages;
    private String height;
    private String weight;
    private SkinTone skinTone;
    private Race race;
    private String education;
    private String ethnicity;
    private String loginToken;

    public UserResponseDto(User user) {
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.middleName = user.getMiddleName();
        this.surname = user.getSurname();
        this.title = user.getTitle();
        this.displayName = user.getDisplayName();
        this.gender = user.getGender();
        this.addressLine1 = user.getAddressLine2();
        this.addressLine2 = user.getAddressLine2();
        this.city = user.getCity();
        this.state = user.getState();
        this.profilePictureUrl = user.getProfilePictureUrl();
        this.userPersonas = user.getUserPersonas();

        this.phoneNumber = user.getPhoneNumber();
        this.status = user.getStatus();
        this.userType = user.getUserType();
        this.isAccountVerified = user.isAccountVerified();
        this.companyName = user.getCompanyName();
        this.isActive = user.isActive();
        this.companyName = user.getCompanyName();
        this.genres = user.getGenres();

        this.height = user.getHeight();
        this.weight = user.getWeight();
        this.skinTone = user.getSkinTone();
        this.race = user.getRace();
        this.education = user.getEducation();
        this.ethnicity = user.getEthnicity();
        this.loginToken = user.getLoginToken();
    }
}
