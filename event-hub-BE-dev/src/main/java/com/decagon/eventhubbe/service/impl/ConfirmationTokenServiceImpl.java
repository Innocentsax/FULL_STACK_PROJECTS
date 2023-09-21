package com.decagon.eventhubbe.service.impl;

import com.decagon.eventhubbe.domain.entities.AppUser;
import com.decagon.eventhubbe.domain.entities.ConfirmationToken;
import com.decagon.eventhubbe.domain.repository.AppUserRepository;
import com.decagon.eventhubbe.domain.repository.ConfirmationTokenRepository;
import com.decagon.eventhubbe.events.register.RegistrationEvent;
import com.decagon.eventhubbe.exception.TokenExpiredException;
import com.decagon.eventhubbe.exception.TokenNotFoundException;
import com.decagon.eventhubbe.service.ConfirmationTokenService;
import com.decagon.eventhubbe.utils.EmailUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final AppUserRepository appUserRepository;
    private final AppUserServiceImpl appUserService;
    private final ApplicationEventPublisher publisher;
    public void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }

    @Override
    public String verifyUser(String token, HttpServletRequest request) {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token)
                .orElseThrow(()->new TokenNotFoundException("Invalid Credential"));
        AppUser appUser = confirmationToken.getAppUser();
        if(appUser.getEnabled().equals(true)){
            return "User already verified, Proceed to login";
        }
        if(confirmationToken.getExpiresAt().before(new Date())){
            confirmationTokenRepository.delete(confirmationToken);
            return "Verification link closed " +
                    " Please click on the link to get a new  verification link " +
                    EmailUtils.applicationUrl(request)+"/api/v1/auth/new-verification-link?email="+appUser.getEmail();

        }
        appUser.setEnabled(true);
        appUserRepository.save(appUser);

        return "USER VERIFIED PROCEED TO LOGIN";
    }

    @Override
    public String sendNewVerificationLink(String email, HttpServletRequest request) {
        AppUser appUser = appUserService.getUserByEmail(email);
        if (appUser.getEnabled().equals(true)){
            return "User already verified, Proceed to login";
        }
        if(confirmationTokenRepository.existsByAppUser(appUser)){
            confirmationTokenRepository.delete(confirmationTokenRepository.findByAppUser(appUser));
        }
        publisher.publishEvent(new RegistrationEvent(appUser, EmailUtils.applicationUrl(request)));
        return "please check your mail for verification link";
    }

    @Override
    public String forgotPassword(String token) {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token)
                .orElseThrow(()->new TokenNotFoundException("Invalid Credential"));
        if(confirmationToken.getExpiresAt().before(new Date())){
            confirmationTokenRepository.delete(confirmationToken);
           throw new TokenExpiredException("Token Expired");

        }

        return confirmationToken.getAppUser().getEmail();
    }
}