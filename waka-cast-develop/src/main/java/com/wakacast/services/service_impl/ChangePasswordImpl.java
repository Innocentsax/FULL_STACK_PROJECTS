package com.wakacast.services.service_impl;

import com.wakacast.dto.ChangePasswordDto;
import com.wakacast.exceptions.UserWithEmailNotFound;
import com.wakacast.models.User;
import com.wakacast.repositories.UserRepository;
import com.wakacast.services.ChangePasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChangePasswordImpl implements ChangePasswordService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String updatePassword(ChangePasswordDto changePasswordDto, String email) {
        User user = userRepository.findUserByEmail(email).orElseThrow(()->new UserWithEmailNotFound("User with email:"+email+ " not found"));
        user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
        userRepository.save(user);
        return "Password has been changed successfully";
    }
}
