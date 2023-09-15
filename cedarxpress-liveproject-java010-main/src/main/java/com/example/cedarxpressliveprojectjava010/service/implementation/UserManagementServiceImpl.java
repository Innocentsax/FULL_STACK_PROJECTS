package com.example.cedarxpressliveprojectjava010.service.implementation;

import com.example.cedarxpressliveprojectjava010.config.jwt.JwtTokenProvider;
import com.example.cedarxpressliveprojectjava010.dto.EmailSenderDto;
import com.example.cedarxpressliveprojectjava010.dto.request.ForgotPasswordRequest;
import com.example.cedarxpressliveprojectjava010.dto.request.ResetPasswordRequest;
import com.example.cedarxpressliveprojectjava010.dto.response.MessageResponse;
import com.example.cedarxpressliveprojectjava010.entity.User;
import com.example.cedarxpressliveprojectjava010.exception.NotFoundException;
import com.example.cedarxpressliveprojectjava010.repository.UserRepository;
import com.example.cedarxpressliveprojectjava010.service.EmailSenderService;
import com.example.cedarxpressliveprojectjava010.service.UserManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.mail.MessagingException;
import java.util.Collections;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserManagementServiceImpl implements UserManagementService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final EmailSenderService emailSenderService;


    @Value("${app.base-url}")
    private String passwordUrl;

    @Override
    public ResponseEntity<MessageResponse> forgotPassword(ForgotPasswordRequest request) throws MessagingException {
        String email = request.getEmail();
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new NotFoundException(
                        "User with email " + email + " doesn't exist!"));

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getEmail(),
                null,
                Collections.singleton(new SimpleGrantedAuthority(String.valueOf(user.getRole())))
        );

        String authorization = jwtTokenProvider.generateToken(authentication);
        String token = authorization.substring(7);


        String forgotPasswordURL = passwordUrl + "/reset-password/" + token;
        String content = "<p>Click the below link to Reset Your Password</p>\n" +
                "<a href=\""+forgotPasswordURL+"\" target=\"_blank\">Reset</a>";


        EmailSenderDto emailSenderDto = new EmailSenderDto(
                request.getEmail(),
                "Reset password link: ",
                content
        );
        emailSenderService.send(emailSenderDto);

        log.info(forgotPasswordURL);
        return ResponseEntity.ok(new MessageResponse("Kindly check email for reset Link!"));
    }

    @Override
    public ResponseEntity<MessageResponse> resetPassword(String jwToken, ResetPasswordRequest request) {
        String email = request.getEmail();
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new NotFoundException(
                        String.format("User with email %s doesn't exist!", email)));
        jwtTokenProvider.validateToken(jwToken);

        if (!request.getNewPassword().equals(request.getConfirmPassword()))
            throw new RuntimeException("Password incorrect!");

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("Password updated successfully"));
    }
}
