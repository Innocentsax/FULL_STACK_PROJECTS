package com.example.hive.utils;

import com.example.hive.entity.User;
import com.example.hive.exceptions.CustomException;
import com.example.hive.repository.UserRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@NoArgsConstructor
public class AuthDetails {


    private UserRepository userRepository;

    @Autowired
    public AuthDetails(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getAuthorizedUser(Principal principal) {
        if (principal != null) {
            var currentUser = principal;
//            final UserDetails currentUser = (UserDetails) ((Authentication) principal).getPrincipal();
            return userRepository.findByEmail(currentUser.getName()).orElseThrow(
                    () -> new CustomException(currentUser.getName())
            );
        } else {
            return null;
        }
    }

    public User getAuthorizedUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByEmail(userDetails.getUsername()).orElseThrow(
                () -> new CustomException(userDetails.getUsername())
        );
    }
}
