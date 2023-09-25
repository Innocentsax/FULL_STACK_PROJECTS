package com.decadev.money.way.service.impl;


import com.decadev.money.way.dto.request.LoginRequest;
import com.decadev.money.way.dto.response.LoginResponse;
import com.decadev.money.way.exception.UserAccountDisabledException;
import com.decadev.money.way.exception.UserNotFoundException;
import com.decadev.money.way.model.JwtToken;
import com.decadev.money.way.model.User;
import com.decadev.money.way.model.VerificationToken;
import com.decadev.money.way.repository.JwtTokenRepository;
import com.decadev.money.way.repository.UserRepository;
import com.decadev.money.way.repository.VerificationTokenRepository;
import com.decadev.money.way.security.JwtService;
import com.decadev.money.way.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;
    private final JwtTokenRepository jwtTokenRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public LoginResponse authenticateUser(LoginRequest request) throws UserNotFoundException, UserAccountDisabledException {
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(()-> new UserNotFoundException("User with email " +request.getEmail() +" not found"));
        if(!user.isEnabled()) throw new UserAccountDisabledException("Account is not enabled yet, Verify your account");

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) throw new UserNotFoundException("Incorrect password");

        revokeToken(user);
        var jwtToken = jwtService.generateToken(user);
        saveToken(user,jwtToken);

        return LoginResponse.builder()
                .token(jwtToken)
                .expiredAt(jwtService.extractExpiration(jwtToken))
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }

    private void saveToken(User user, String jwtToken) {
        JwtToken token = JwtToken.builder()
                .token(jwtToken)
                .expired(false)
                .revoked(false)
                .user(user)
                .build();
        jwtTokenRepository.save(token);
    }

    private void revokeToken(User user){
        var validTokenByUser= jwtTokenRepository.findTokenByUserAndExpiredIsFalseAndRevokedIsFalse(user);

        if(validTokenByUser.isEmpty()) return;

        validTokenByUser.forEach(token->{
            token.setRevoked(true);
            token.setExpired(true);
        });

        jwtTokenRepository.saveAll(validTokenByUser);
    }

    @Override
    public String validateUserVerificationToken(String theToken) {
        VerificationToken token = verificationTokenRepository.findByToken(theToken);
        if (token == null){return "Invalid verification token";}
        User user= token.getUser();
        if (user.isEnabled()){return "Already verified";}

        Calendar calendar= Calendar.getInstance();
        if (token.getExpirationTime().getTime() - calendar.getTime().getTime()<=0){
            verificationTokenRepository.delete(token);
            return "Token already expired";
        }
        user.setEnabled(true);
        userRepository.save(user);
//      verificationTokenRepository.delete(token);
        return "Verified";
    }

}
