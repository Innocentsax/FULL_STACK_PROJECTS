package com.decagon.fintechpaymentapisqd11a.services.serviceImpl;

import com.decagon.fintechpaymentapisqd11a.dto.MailServiceDto;
import com.decagon.fintechpaymentapisqd11a.models.Users;
import com.decagon.fintechpaymentapisqd11a.repositories.UsersRepository;
import com.decagon.fintechpaymentapisqd11a.request.ForgotPasswordRequest;
import com.decagon.fintechpaymentapisqd11a.request.PasswordRequest;
import com.decagon.fintechpaymentapisqd11a.util.Constant;
import com.decagon.fintechpaymentapisqd11a.util.JwtUtils;
import com.decagon.fintechpaymentapisqd11a.dto.LoginRequestPayload;
import com.decagon.fintechpaymentapisqd11a.exceptions.UserNotFoundException;
import com.decagon.fintechpaymentapisqd11a.services.LoginService;
import com.decagon.fintechpaymentapisqd11a.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.InputMismatchException;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginServiceImpl implements LoginService {
    private final AuthenticationManager userAuthenticationManager;
    private final JwtUtils jwtUtils;
    private final UserDetailsService userService;
    private final UsersRepository usersRepository;
    private final MailServiceImpl mailService;
    private final Util utils;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String authenticate(LoginRequestPayload loginDto) throws Exception {
        try {
            userAuthenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new UserNotFoundException("Invalid Credentials");
        }
        final UserDetails userDetails = userService.loadUserByUsername(loginDto.getEmail());
        return jwtUtils.generateToken(userDetails);
    }

    @Override
    public String generateResetToken(ForgotPasswordRequest forgotPasswordRequest) {
        String email = forgotPasswordRequest.getEmail();
        Users users = usersRepository.findUsersByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));


        String token = jwtUtils.generatePasswordResetToken(email);
        String link = Constant.RESET_PASSWORD_LINK + token;
        log.info("click here to reset your password" + link);
        sendPasswordResetEmail(users.getFirstName(), forgotPasswordRequest.getEmail(),link);

        return "Check your email to reset your password";
    }

    @Override
    public void sendPasswordResetEmail(String name, String email, String link) {
        String subject = "Reset your password";
        String body = "Please click the link below to reset your password";
        body += " " + link;

        MailServiceDto mailServiceDto = new MailServiceDto(name, email, body, subject);
        mailService.sendNotification(mailServiceDto);

    }

    @Override
    public String resetPassword(PasswordRequest passwordRequest, String token) {
        boolean passwordMatch = utils.validatePassword
                (passwordRequest.getNewPassword(), passwordRequest.getConfirmPassword());
        if(!passwordMatch){
            throw new InputMismatchException("Passwords do not match");
        }
        String email = jwtUtils.extractUsername(token);
        Users users = usersRepository.findUsersByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        users.setPassword(passwordEncoder.encode(passwordRequest.getNewPassword()));
        usersRepository.save(users);
        return "Password reset successful";
    }
}
