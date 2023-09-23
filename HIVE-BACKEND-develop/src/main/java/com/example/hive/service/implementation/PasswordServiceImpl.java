package com.example.hive.service.implementation;

import com.example.hive.dto.request.ResetPasswordDto;
import com.example.hive.entity.PasswordResetToken;
import com.example.hive.entity.User;
import com.example.hive.exceptions.CustomException;
import com.example.hive.repository.PasswordResetTokenRepository;
import com.example.hive.repository.UserRepository;
import com.example.hive.service.PasswordService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.mail.internet.MimeMessage;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PasswordServiceImpl implements PasswordService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final JavaMailSender mailSender;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken passwordResetToken = new PasswordResetToken(token, user);
        passwordResetTokenRepository.save(passwordResetToken);
    }

    @Override
    public String passwordResetTokenMail(User user, String applicationUrl, String token) {
//        String url = applicationUrl + "/savePassword?token=" + token;

        String url = "http://localhost:3000/reset-password/" + token;

        log.info("Click the link to reset your password: {}", url);

        return url;
    }

    @Override
    public String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    @Override
    public void sendEmail(String recipientEmail, String link)
            throws UnsupportedEncodingException, MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("hive@blessingchuks.tech", "Hive Support");
        helper.setTo(recipientEmail);

        String subject = "Here's the link to reset your password";

        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";

        helper.setSubject(subject);

        helper.setText(content, true);
        mailSender.send(message);
    }

    @Override
    public String validatePasswordResetToken(String token) {
        Optional<PasswordResetToken> passwordResetToken = Optional.of(passwordResetTokenRepository.findByToken(token).orElseThrow());

        if(passwordResetToken.isEmpty()) {
            return "invalid token";
        }

        User user = passwordResetToken.get().getUser();
        Calendar calender = Calendar.getInstance();

        if(passwordResetToken.get().getDate().getTime() - calender.getTime().getTime() <= 0) {
            passwordResetTokenRepository.delete(passwordResetToken.get());
            return "Expired";
        }
        return "valid";
    }

    @Override
    public void changePassword(User user, ResetPasswordDto passwordDto) {
        if (passwordDto.getNewPassword().equals((passwordDto.getConfirmPassword()))) {
            user.setPassword((passwordEncoder.encode(passwordDto.getNewPassword())));
            userRepository.save(user);
        }
       else { throw new CustomException("Password does not match");}
    }
}