package com.decagon.OakLandv1be.entities;

import com.decagon.OakLandv1be.enums.Gender;
import com.decagon.OakLandv1be.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "person_tbl")
public class Person extends BaseEntity{

    @NotNull(message = "First name cannot be missing or empty")
    @Column(nullable = false, length = 50)
    private String firstName;

    @NotNull(message = "Last name cannot be missing or empty")
    @Column(nullable = false, length = 50)
    private String lastName;

    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    private String date_of_birth;

    private String phone;

    private Boolean verificationStatus;


    @Column(nullable = false)
    private String password;

    private String address;

    private boolean isActive = true;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @JsonIgnore
    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
    private Admin admin;

    @JsonIgnore
    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
    private SuperAdmin superAdmin;

    @JsonIgnore
    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
    private Customer customer;
}
