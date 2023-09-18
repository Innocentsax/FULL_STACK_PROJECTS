package com.example.decapay.services.impl;

import com.example.decapay.exceptions.WrongPasswordException;
import com.example.decapay.models.User;
import com.example.decapay.pojos.requestDtos.PasswordUpdateRequest;
import com.example.decapay.repositories.UserRepository;
import com.example.decapay.services.PasswordUpdateService;
import com.example.decapay.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PasswordUpdateServiceImpl  implements PasswordUpdateService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserUtil userUtil;

    @Override
    public void updatePassword(PasswordUpdateRequest passwordUpdateRequest) {

        String password = passwordUpdateRequest.getPassword();
        String newPassword = passwordUpdateRequest.getNewPassword();
        String confirmPassword = passwordUpdateRequest.getConfirmPassword();

        String email = userUtil.getAuthenticatedUserEmail();

        Optional<User> optionalUser = userRepository.findByEmail(email);

        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            String encodedPassword = user.getPassword();
            boolean isPasswordAmatch = passwordEncoder.matches(password, encodedPassword);

            if(!isPasswordAmatch) throw new WrongPasswordException(HttpStatus.BAD_REQUEST, "incorrect password" );

            boolean isPasswordEquals = newPassword.equals(confirmPassword);
            if(!isPasswordEquals) throw new WrongPasswordException(HttpStatus.BAD_REQUEST, "new password does not match");

            user.setPassword(passwordEncoder.encode(newPassword));

            userRepository.save(user);
        }
    }
}
