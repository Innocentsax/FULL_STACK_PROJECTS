package com.hrsupportcentresq014.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hrsupportcentresq014.entities.entityUtil.Socials;
import com.hrsupportcentresq014.utils.Social;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
@Builder
public class Employee extends BaseEntity implements UserDetails{

    private String firstName;

    private String nickName;

    private String phoneNo;

    @Indexed(unique = true)
    private String email;

    private String lastName;

    private String password;

    private Role role;

    private String department;

    private LocalDate startDate;

    private String workLocation;

    private String contractType;
    private Social social;
    private String imageUrl;
    private String resumeUrl;


    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    private String address;

    private NextOfKin nextOfKin;
     private String nationality;

    private String maritalStatus;

    private String position;

    private String reportTo;
    private boolean loggedIn;
    private String teamManager;

    public Employee(String firstName,  String lastName,  String email, String position) {
        this.firstName = firstName;
        this.email = email;
        this.lastName = lastName;
        this.position = position;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isLoggedIn();
    }
}
