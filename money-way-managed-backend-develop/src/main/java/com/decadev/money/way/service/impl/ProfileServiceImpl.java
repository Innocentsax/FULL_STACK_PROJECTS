package com.decadev.money.way.service.impl;

import com.decadev.money.way.dto.response.ProfileDTO;
import com.decadev.money.way.exception.UserNotFoundException;
import com.decadev.money.way.model.User;
import com.decadev.money.way.repository.UserRepository;
import com.decadev.money.way.service.ProfileService;
import com.decadev.money.way.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;
    private final SecurityUtils securityUtils;


    @Override
    public ProfileDTO getProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("User Not Found"));

        return ProfileDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .address(user.getAddress())
                .accountNumber(user.getWallet().getAccountNumber())
                .accountBalance(user.getWallet().getAccountBalance())
                .bank(user.getWallet().getBankName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }

    @Override
    public String updateProfile(ProfileDTO profileDTO) throws UserNotFoundException {
        User user = userRepository.findByEmail(securityUtils.getCurrentUserDetails().getUsername()).orElseThrow(()->new UserNotFoundException("User not found"));

        user.setEmail(profileDTO.getEmail());
        user.setAddress(profileDTO.getAddress());
        user.setFirstName(profileDTO.getFirstName());
        user.setLastName(profileDTO.getLastName());
        user.setPhoneNumber(profileDTO.getPhoneNumber());

        userRepository.save(user);
        return "Profile updated successfully";
    }
}
