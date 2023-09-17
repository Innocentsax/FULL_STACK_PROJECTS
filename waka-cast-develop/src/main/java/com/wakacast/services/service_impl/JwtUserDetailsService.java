package com.wakacast.services.service_impl;

import com.wakacast.dto.MyUserDetails;
import com.wakacast.exceptions.AccountNotEnabledException;
import com.wakacast.models.User;
import com.wakacast.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JwtUserDetailsService implements  UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        Optional<User> userModel = userRepository.findUserByEmail(userEmail);
        User user = userModel.orElseThrow(() ->
              new UsernameNotFoundException("No user found with email : " + userEmail));

        if(user.isAccountVerified() && user.isActive()){
            return new MyUserDetails(user);
        }else if(!user.isAccountVerified())
            throw new AccountNotEnabledException("Account is not Verified");
        else
            throw new AccountNotEnabledException("Account is not enabled");
    }
}
