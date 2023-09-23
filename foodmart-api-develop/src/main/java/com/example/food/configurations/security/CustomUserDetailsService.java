package com.example.food.configurations.security;

import com.example.food.Enum.Role;
import com.example.food.model.Users;
import com.example.food.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;


@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email: " + email));
        return new User(user.getEmail(), user.getPassword(), getGrantedAuthorities(user.getRole() == null ? Role.ROLE_USER : user.getRole()));
    }

    private Collection<? extends GrantedAuthority> getGrantedAuthorities(Role roles) {
        return Collections
                .singleton(new SimpleGrantedAuthority(roles.toString()));
    }
}
