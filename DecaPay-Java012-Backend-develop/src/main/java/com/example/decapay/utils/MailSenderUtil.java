package com.example.decapay.utils;

import com.example.decapay.configurations.mails.EmailSenderServiceImpl;
import com.example.decapay.configurations.security.CustomUserDetailService;
import com.example.decapay.configurations.security.JwtUtils;
import com.example.decapay.enums.Status;
import com.example.decapay.enums.VerificationType;
import com.example.decapay.models.Token;
import com.example.decapay.models.User;
import com.example.decapay.pojos.mailDto.MailDto;
import com.example.decapay.repositories.TokenRepository;
import com.example.decapay.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
@RequiredArgsConstructor
public class MailSenderUtil {

    private final EmailSenderServiceImpl emailService;

    private final CustomUserDetailService customUserDetailService;

    private final TokenRepository tokenRepository;

    private  final JwtUtils jwtUtils;

    @Value("${verify.email.url:http://localhost:8082/api/v1/auth/verify-token/}")
    private String verifyEmailUrl;

    final String SUBJECT = "One last step to complete your registration with DecaPay!";

    final String PASSWORD_RESET_SUBJECT = "Password reset request";

    final String HTMLBODY = "`Please verify your email address`"
            + "Thank you for registering with our application. To complete registration process and be able to log in,"
            + " click on the following link: "
            +  "<a href='link=$tokenValue'>"
            + "Final step to complete your registration" + "</a>"
            + "Thank you! And we are waiting for you inside!";


    final String PASSWORD_RESET_HTMLBODY = "`<h1>A request to reset your password</h1>`"
            + "<p>Hi, $firstName!</p> "
            + "<p>Someone has requested to reset your password with our project. If it were not you, please ignore it."
            + " otherwise please click on the link below to set a new password: "
            + "<a href='link?token=$tokenValue'>"
            + " Click this link to Reset Password"
            + "</a><br/><br/>"
            + "Thank you!";


    public void verifyMail(User user) throws MessagingException {




        UserDetails userDetails = customUserDetailService.loadUserByUsername(user.getEmail());
        String generatedToken = jwtUtils.generateToken(userDetails);

        Token token = new Token();
        token.setToken(generatedToken);
        token.setStatus(Status.ACTIVE);
        token.setVerificationType(VerificationType.REGISTRATION);
        token.setUser(user);

        tokenRepository.save(token);

        String link = String.format("%s%s",verifyEmailUrl, generatedToken);

        final String registrationMessage = String.format("Thank you for registering with our application. To complete registration process click on the link below \n %s",link);


        MailDto mailDto = new MailDto(user.getEmail(), SUBJECT, registrationMessage);
        emailService.sendEmail(mailDto);
    }
}
