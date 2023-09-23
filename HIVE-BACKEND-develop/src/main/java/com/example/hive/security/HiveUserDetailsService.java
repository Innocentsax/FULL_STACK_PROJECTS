package com.example.hive.security;

import com.example.hive.entity.User;
import com.example.hive.exceptions.ResourceNotFoundException;
import com.example.hive.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
@Log4j2
@RequiredArgsConstructor
public class HiveUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("userService loadUserByUserName - email :: [{}] ::", email);
        log.info("User ==> [{}]", userRepository.findByEmail(email));
        User user = (User) userRepository.findByEmail(email)
                .orElseThrow(
                        () -> {
                            throw new ResourceNotFoundException("user does not exist");
                        }
                );
        Collection<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority(user.getRole().name()));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);

    }
}

