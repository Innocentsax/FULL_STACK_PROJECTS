package com.decagon.eventhubbe.service.impl;

import com.decagon.eventhubbe.domain.entities.AppUser;
import com.decagon.eventhubbe.domain.entities.ConfirmationToken;
import com.decagon.eventhubbe.domain.repository.AppUserRepository;
import com.decagon.eventhubbe.domain.repository.ConfirmationTokenRepository;
import com.decagon.eventhubbe.dto.request.ResetPasswordRequest;
import com.decagon.eventhubbe.events.password.ForgotPasswordEvent;
import com.decagon.eventhubbe.exception.SamePasswordException;
import com.decagon.eventhubbe.service.PasswordService;
import com.decagon.eventhubbe.utils.EmailUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordServiceImpl implements PasswordService {
    private final AppUserServiceImpl appUserService;
    private final AppUserRepository appUserRepository;
    private final ConfirmationTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher publisher;

    @Override
    public String forgotPassword(String email, HttpServletRequest request) {
        AppUser appUser = appUserService.getUserByEmail(email);
        ConfirmationToken confirmationToken = tokenRepository.findByAppUser(appUser);
        if(confirmationToken!= null){
            tokenRepository.delete(confirmationToken);
        }
        publisher.publishEvent(new ForgotPasswordEvent(appUser, EmailUtils.frontEndAppUrl(request)));
        return "Please Check Your Mail For Password Reset Link";
    }
    @Override
    public String resetPassword(ResetPasswordRequest request){
        AppUser appUser = appUserService.getUserByEmail(request.getEmail());
        if(passwordEncoder.matches(request.getPassword(), appUser.getPassword())){
            throw new SamePasswordException("Please Choose a Different Password");
        }
        appUser.setPassword(passwordEncoder.encode(request.getPassword()));
        appUserRepository.save(appUser);
        return "Password Changed Successfully";
    }

}
