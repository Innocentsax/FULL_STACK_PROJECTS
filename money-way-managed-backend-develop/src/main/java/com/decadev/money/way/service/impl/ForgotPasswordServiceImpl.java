package com.decadev.money.way.service.impl;

import com.decadev.money.way.dto.request.ResetPassword;
import com.decadev.money.way.event.EventListener;
import com.decadev.money.way.model.ForgotPassword;
import com.decadev.money.way.model.User;
import com.decadev.money.way.repository.ForgotPasswordRepository;
import com.decadev.money.way.repository.UserRepository;
import com.decadev.money.way.service.ForgotPasswordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ForgotPasswordServiceImpl implements ForgotPasswordService {
    private final ForgotPasswordRepository forgotPasswordRepo;
    private final EventListener eventListener;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;



    @Override
    public ResponseEntity<String> resetForgotPassword(String email) {
        String resetToken;
        resetToken = UUID.randomUUID().toString();
        LocalDateTime expirationDate = LocalDateTime.now().plusMinutes(5);
        ForgotPassword resetPassword = new ForgotPassword();
        resetPassword .setEmail(email);
        resetPassword .setResetToken(resetToken);
        resetPassword .setExpirationDate(expirationDate);
        forgotPasswordRepo.save(resetPassword);
        String resetEmailUrl = "http://localhost:9000/api/v1/password/reset-password?token=" + resetToken;

        eventListener.sendForgotPasswordVerificationEmail(resetEmailUrl, email);
        return ResponseEntity.ok("Sent");
    }


    @Override
    public ResponseEntity<String> ResetPassword(String token, ResetPassword resetPassword) {
        if(resetPassword.getNewPassword().isBlank() || resetPassword.getNewPassword().isBlank()){
            throw new UsernameNotFoundException("please do not leave any field empty");
        }

        ForgotPassword forgotPassDetail = forgotPasswordRepo.findByResetToken(token)
                .orElseThrow(()-> new UsernameNotFoundException("token not found"));

        if(forgotPassDetail.getExpirationDate().isBefore(LocalDateTime.now())){
            throw new UsernameNotFoundException("token expired");
        }
        if(!resetPassword.getNewPassword().equals(resetPassword.getConfirmPassword())){
            throw new UsernameNotFoundException("password do not match");
        }
        User user = userRepository.findByEmail(forgotPassDetail.getEmail())
                .orElseThrow(()->new UsernameNotFoundException("User Not Found"));

        user.setPassword(passwordEncoder.encode(resetPassword.getNewPassword()));
        userRepository.save(user);

        forgotPasswordRepo.delete(forgotPassDetail);

        return ResponseEntity.ok("reset successful");
    }

    @Override
    public ResponseEntity<Boolean> validToken(String token) {

        ForgotPassword forgotPassDetail = forgotPasswordRepo.findByResetToken(token)
                .orElseThrow(()-> new UsernameNotFoundException("token not found"));

        if(forgotPassDetail.getExpirationDate().isBefore(LocalDateTime.now())){
            throw new UsernameNotFoundException("token expired");
        }

        return ResponseEntity.ok(true);
    }

}

