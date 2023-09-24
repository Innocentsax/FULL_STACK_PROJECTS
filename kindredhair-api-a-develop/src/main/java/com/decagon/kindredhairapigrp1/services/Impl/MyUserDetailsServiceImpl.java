package com.decagon.kindredhairapigrp1.services.Impl;


import com.decagon.kindredhairapigrp1.DTO.UserAuthDTO;
import com.decagon.kindredhairapigrp1.models.User;
import com.decagon.kindredhairapigrp1.repository.UserRepository;
import com.decagon.kindredhairapigrp1.services.MyUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("myUserDetailsService")
public class MyUserDetailsServiceImpl implements MyUserDetailsService {
    private UserRepository userRepository;

    MyUserDetailsServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    /**
     * Method retrieves user by email and sends the needed authentication field to spring boot
     * security for user authentication
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user  = userRepository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("Invalid User Email"));
        return new UserAuthDTO(user.getEmail(),  user.getPassword(), user.getRole());
    }

}
