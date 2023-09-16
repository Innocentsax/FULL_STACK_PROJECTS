package com.decagon.chompapp.controllers;

import com.decagon.chompapp.exceptions.UserNotFoundException;
import com.decagon.chompapp.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@TestConfiguration
public class SpringSecurityForUserControllerImplTestConfig {

    @Bean
    public User testUser() {
        return User.builder()
                .firstName("wilfred")
                .lastName("ighalo")
                .username("username")
                .email("username@example.com")
                .isActive(true)
                .password("password")
                .build();
    }

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {

        UserDetails userDetails = new UserDetails() {

            final User testUser = testUser();

            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                Set<GrantedAuthority> rolesSet = new HashSet<>();
                rolesSet.add(new SimpleGrantedAuthority("PREMIUM"));
                return rolesSet;
            }

            @Override
            public String getPassword() {
                return testUser.getPassword();
            }

            @Override
            public String getUsername() {
               return testUser.getUsername();
            }

            @Override
            public boolean isAccountNonExpired() {
                return testUser.getIsActive();
            }

            @Override
            public boolean isAccountNonLocked() {
                return testUser().getIsActive();
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return testUser.getIsActive();
            }

            @Override
            public boolean isEnabled() {
                return testUser().getIsActive();
            }
        };

        return new UserDetailsManager() {

            private final InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager(List.of(userDetails));


            @Override
            public void createUser(UserDetails userDetails) {
                this.inMemoryUserDetailsManager.createUser(userDetails);
            }

            @Override
            public void updateUser(UserDetails userDetails) {
                this.inMemoryUserDetailsManager.updateUser(userDetails);
            }

            @Override
            public void deleteUser(String s) {
                this.inMemoryUserDetailsManager.deleteUser(s);
            }

            @Override
            public void changePassword(String s, String s1) {
                this.inMemoryUserDetailsManager.changePassword(s, s1);
            }

            @Override
            public boolean userExists(String s) {
                return this.inMemoryUserDetailsManager.userExists(s);
            }

            @Override
            public UserDetails loadUserByUsername(String s) throws UserNotFoundException {
                return userDetails;
            }
        };
    }
}
