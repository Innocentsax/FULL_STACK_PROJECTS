package com.goCash.entities;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Slf4j
public class CustomUserDetails implements UserDetails {
    private final String userName;
    private final String password;
    private final boolean isEnabled;
    private final List<GrantedAuthority> authorities;

    public CustomUserDetails(User appUser) {
        log.info("When utilizing Spring Security for authentication and authorization in our application, " +
                "user-specific data must be provided to Spring Security API and used during the authentication process. " +
                "This user-specific data is encapsulated in the UserDetails object");

        this.userName = appUser.getEmail();
        this.password = appUser.getPassword();
        this.isEnabled = appUser.isVerified();
        this.authorities = new ArrayList<>(List.of(new SimpleGrantedAuthority(appUser.getRole().name())));
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
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
        return this.isEnabled;
    }
}
