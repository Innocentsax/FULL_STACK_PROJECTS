package com.goCash.security;

import com.goCash.entities.CustomUserDetails;
import com.goCash.entities.User;
import com.goCash.repository.UserRepository;
import com.goCash.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Loading the user details from the database to Spring Security " +
                "During authentication process");

        //Get the user from the database
        User appUser = userRepository.findByEmail(username).orElse(null);
        log.info(username);
        if (appUser == null)
        {
            return (UserDetails) ApiResponse.builder().code("01").message("User not found")
                    .httpStatus(HttpStatus.NO_CONTENT);
        }
        log.info("User details returned as UserDetails object");
        return new CustomUserDetails(appUser);
    }
}
