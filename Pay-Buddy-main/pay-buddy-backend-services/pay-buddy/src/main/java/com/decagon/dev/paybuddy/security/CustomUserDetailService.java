package com.decagon.dev.paybuddy.security;

import com.decagon.dev.paybuddy.models.Authority;
import com.decagon.dev.paybuddy.models.Role;
import com.decagon.dev.paybuddy.models.User;
import com.decagon.dev.paybuddy.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

@AllArgsConstructor
@Service
@JsonComponent
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->new UsernameNotFoundException("User not found with email: " + email));
        String password = user.getPassword() == null || user.getPassword().isEmpty() ? "****" : user.getPassword();
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), password, user.getIsEmailVerified(), true, true, true, getAuthorities(user));
    }

    public Collection<? extends GrantedAuthority> getAuthorities(User user) {
        Collection<GrantedAuthority> authorities = new HashSet<>();
        Collection<Role> roles = user.getRoles();
        Collection<Authority> authorityCollection = new HashSet<>();
        if (roles == null) return  authorities;
        roles.forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
            authorityCollection.addAll(role.getAuthorities());
        });
        authorityCollection.forEach(authority -> {
            authorities.add(new SimpleGrantedAuthority(authority.getName()));
        });
        return authorities;
    }
}
