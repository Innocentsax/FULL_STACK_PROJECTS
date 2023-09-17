package com.wakacast.models;

import com.wakacast.enums.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "user_table")
public class User extends BaseClass{
    private String email;
    private String firstName;
    private String middleName;
    private String surname;
    private String title;
    private String displayName;
    private String password;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @ManyToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    private Set<Role> roles = new HashSet<>();
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private LocalDate dateOfBirth;
    private String profilePictureUrl;
    @ManyToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    private Set<UserPersona> userPersonas = new HashSet<>();
    private String phoneNumber;
    private String workPhoneNumber;
    private String headline;
    private String status;
    @Enumerated(EnumType.STRING)
    private UserType userType;
    private boolean isAccountVerified;
    private boolean isActive = true;
    private String companyName;
    private String country;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Portfolio> portfolios = new HashSet<>();
    @ManyToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    private Set<Genre> genres = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL)
    private Set<SpokenLanguage> spokenLanguages = new HashSet<>();
    private String height;
    private String weight;
    @Enumerated(EnumType.STRING)
    private SkinTone skinTone;
    @Enumerated(EnumType.STRING)
    private Race race;
    private String education;
    private String ethnicity;
    private String loginToken;
}


