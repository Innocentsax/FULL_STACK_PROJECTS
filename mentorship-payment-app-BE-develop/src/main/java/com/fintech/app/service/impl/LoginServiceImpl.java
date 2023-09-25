package com.fintech.app.service.impl;

import com.fintech.app.model.User;
import com.fintech.app.repository.BlacklistedTokenRepository;
import com.fintech.app.repository.UserRepository;
import com.fintech.app.request.LoginRequest;
import com.fintech.app.request.PasswordRequest;
import com.fintech.app.response.BaseResponse;
import com.fintech.app.response.JwtAuthResponse;
import com.fintech.app.security.JwtTokenProvider;
import com.fintech.app.service.BlacklistService;
import com.fintech.app.service.LoginService;
import com.fintech.app.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginServiceImpl implements LoginService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final HttpServletResponse httpServletResponse;
    private final BlacklistService blacklistService;
    private final HttpServletRequest httpServletRequest;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JavaMailSender mailSender;



    @Override
    public BaseResponse<JwtAuthResponse> login(LoginRequest loginRequest) throws Exception {
        Authentication authentication;
        String token;
        String message = "Login Successful";

        try{
            Authentication auth =  new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),loginRequest.getPassword());

            authentication = authenticationManager.authenticate(auth);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenProvider.generateToken(authentication);
            httpServletResponse.setHeader("Authorization", token);
        }
        catch (BadCredentialsException ex){
            throw new Exception("incorrect user credentials", ex);
        }
        return new BaseResponse<>(HttpStatus.OK, message, new JwtAuthResponse(token));
    }

    @Override
    public BaseResponse<?> logout() {

        String token = httpServletRequest.getHeader("Authorization");

        blacklistService.blacklistToken(token);

        return new BaseResponse<>(HttpStatus.OK, "Logout Successful", null);

    }

    @Override
    public BaseResponse<String> changePassword(PasswordRequest passwordRequest) {

        if(!passwordRequest.getNewPassword().equals(passwordRequest.getConfirmPassword())){
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, "new password must be the same with confirm password", null);
        }

        String loggedInUsername =  SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findUserByEmail(loggedInUsername);

        if (user == null) {
            return new BaseResponse<>(HttpStatus.UNAUTHORIZED, "User not logged In", null);
        }

        boolean matchPasswordWithOldPassword = passwordEncoder.matches(passwordRequest.getOldPassword(), user.getPassword());

        if(!matchPasswordWithOldPassword){
           return new BaseResponse<>(HttpStatus.BAD_REQUEST, "old password is not correct", null);
        }
        user.setPassword(passwordEncoder.encode(passwordRequest.getNewPassword()));

        userRepository.save(user);
        return new BaseResponse<>(HttpStatus.OK, "password changed successfully", null);
    }

    @Override
    public BaseResponse<String> generateResetToken(PasswordRequest passwordRequest) throws MessagingException {
        String email = passwordRequest.getEmail();
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            return new BaseResponse<>(HttpStatus.NOT_FOUND, "User with email not found", null);
        }


        String token = jwtTokenProvider.generatePasswordResetToken(email);
        String url = "http://localhost:3000/resetPassword?token=" + token;

        log.info("click here to reset your password: " + url);
        sendPasswordResetEmail(user, url);
        return new BaseResponse<>(HttpStatus.OK,"Check Your Email to Reset Your Password",url);
    }

    private void sendPasswordResetEmail(User user, String url) {
        String subject = "Reset your password";
        String senderName = "Fintech App";
        String mailContent = "<p> Dear "+ user.getLastName() +", </p>";
        mailContent += "<p> Please click the link below to reset your password, </p>";
        mailContent += "<h3><a href=\""+ url + "\"> RESET PASSWORD </a></h3>";
        Util.sendMail(user, subject, senderName, mailContent, mailSender);
    }

    @Override
    public BaseResponse<String> resetPassword(PasswordRequest passwordRequest, String token) {
        if (!passwordRequest.getNewPassword().equals(passwordRequest.getConfirmPassword())) {
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, "Passwords don't match.", null);
        }
        String email = jwtTokenProvider.getUsernameFromJwt(token);

        User user = userRepository.findUserByEmail(email);

        if (user == null) {
            return new BaseResponse<>(HttpStatus.NOT_FOUND, "User with email " + email + " not found", null);
        }

        user.setPassword(passwordEncoder.encode(passwordRequest.getNewPassword()));
        userRepository.save(user);
        return new BaseResponse<>(HttpStatus.OK,"Password Reset Successfully",null);
    }


}
