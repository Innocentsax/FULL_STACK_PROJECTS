package com.goCash.services.implementations;

import com.goCash.dto.request.PasswordResetEmailRequest;
import com.goCash.dto.request.PasswordResetNewPasswordRequest;
import com.goCash.dto.request.SendEmailDto;
import com.goCash.entities.PasswordResetToken;
import com.goCash.entities.User;
import com.goCash.exception.UserNotFoundException;
import com.goCash.repository.PasswordResetTokenRepository;
import com.goCash.repository.UserRepository;
import com.goCash.services.EmailServices;
import com.goCash.services.PasswordService;
import com.goCash.utils.email.Subject;
import com.goCash.utils.email.Template;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PasswordServiceImpl implements PasswordService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final EmailServices emailServices;

    @Value("${localhost.baseurl}")
    private String baseUrl;

    private void createPasswordResetTokenForUser(User user, String passwordToken) {
        log.info("Creating password reset token for user: {}", user.getEmail());
        PasswordResetToken passwordResetToken = new PasswordResetToken(passwordToken, user);
        passwordResetTokenRepository.save(passwordResetToken);
        log.info("Password reset token created successfully.");
    }

    private String validatePasswordResetToken(String theToken) {
        log.info("Validating password reset token: {}", theToken);
        PasswordResetToken passwordToken = passwordResetTokenRepository.findByToken(theToken);

        if (passwordToken == null) {
            log.warn("Invalid password reset token: {}", theToken);
            return "Invalid password reset token";
        }

        User user = passwordToken.getUser();
        LocalDateTime expirationTime = passwordToken.getTokenExpirationTime();

        if (LocalDateTime.now().isAfter(expirationTime)) {
            log.warn("Link already expired for user: {}", user.getEmail());
            return "Link already expired, resend link";
        }

        log.info("Password reset token is valid for user: {}", user.getEmail());
        return "valid";
    }

    private Optional<User> findUserByPasswordToken(String passwordResetToken) {
        log.info("Finding user by password reset token: {}", passwordResetToken);
        Optional<User> user = Optional.ofNullable(passwordResetTokenRepository.findByToken(passwordResetToken).getUser());
        if (user.isPresent()) {
            log.info("User found: {}", user.get().getEmail());
        } else {
            log.info("No user found for password reset token: {}", passwordResetToken);
        }
        return user;
    }

    public String resetPasswordRequest(PasswordResetEmailRequest emailRequest) {
        log.info("Received password reset request for email: {}", emailRequest.getEmail());

        User user = findByEmail(emailRequest.getEmail()).orElseThrow(() -> new UserNotFoundException("User not found"));

        if (user != null) {
            String passwordResetToken = UUID.randomUUID().toString();
            createPasswordResetTokenForUser(user, passwordResetToken);

            String link = baseUrl + "/auth/reset-password?token=";
            SendEmailDto sendEmailDto = new SendEmailDto();

            sendEmailDto.setToEmail(emailRequest.getEmail());
            sendEmailDto.setSubject(Subject.FORGOT_PASSWORD);
            sendEmailDto.setMessage(Template.passwordResetEmail(user.getFirstName(), passwordResetToken, link));

            emailServices.sendMessage(sendEmailDto);
            log.info("Password reset token generated for user with email: {}", emailRequest.getEmail());
        } else {
            log.info("No user found with email: {}", emailRequest.getEmail());
        }
        return "sent";
    }

    public String resetPassword(PasswordResetNewPasswordRequest newPasswordRequest, String token) {
    log.info("Received a request to reset password for token: {}", token);

    try {
        String tokenVerificationResult = validatePasswordResetToken(token);
        log.debug("Token verification result: {}", tokenVerificationResult);

        if (!tokenVerificationResult.equalsIgnoreCase("valid")) {
            log.warn("Invalid token password reset token: {}", token);
            return "Invalid password reset token";
        }

        Optional<User> theUser = findUserByPasswordToken(token);
        if (theUser.isPresent()) {
            log.info("Found the user for token: {}", token);

            if (!newPasswordRequest.getNewPassword().equals(newPasswordRequest.getConfirmPassword())) {
                log.warn("New password and confirm password do not match");
                return "New password and confirm password do not match";
            }

            changePassword(theUser.get(), newPasswordRequest.getNewPassword());
            log.info("Password has been reset successfully for user: {}", theUser.get().getEmail());
            return "Password has been reset successfully";
        } else {
            log.warn("No user found for password reset token: {}", token);
            return "No user found for password reset token";
        }
    } catch (Exception e) {
        log.error("An error occurred during password reset: {}", e.getMessage());
        return "An error occurred during password reset";
    }
}

    private void changePassword(User theUser, String newPassword) {
        theUser.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(theUser);
    }

    private Optional<User> findByEmail(String email) {
        try {
            return userRepository.findByEmail(email);
        } catch (Exception e) {
            log.error("An error occurred while finding user by email: {}", e.getMessage());
            return Optional.empty();
        }
    }
}
