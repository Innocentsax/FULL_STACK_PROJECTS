package com.decagon.fitnessoapp.service.serviceImplementation;

import com.decagon.fitnessoapp.model.user.Person;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class PersonDetails implements UserDetails {

    private String name;
    private String password;
    private boolean active;
    private List<SimpleGrantedAuthority> authorities;

    public PersonDetails(Person person) {
        this.name = person.getUserName();
        this.password = person.getPassword();
        this.active = person.isVerifyEmail();
        this.authorities = person.getRoleDetail().getGrantedAuthorities();
    }

    public PersonDetails() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
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
        return active;
    }
}