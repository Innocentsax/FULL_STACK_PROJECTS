package com.fintech.app.util;

import com.fintech.app.model.User;
import com.fintech.app.model.VerificationToken;
import com.fintech.app.request.UserRequest;
import com.fintech.app.response.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

@Component
@Slf4j
public class Util {

//    @Value("${TEMP_APP_URL}")
//    private String TEMP_APP_URL;
    private final JavaMailSender mailSender;

    public Util(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public User requestToUser(UserRequest userRequest) {
        return User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .email(userRequest.getEmail())
                .phoneNumber(userRequest.getPhoneNumber())
                .bvn(userRequest.getBvn())
                .createdAt(LocalDateTime.now())
                .modifyAt(LocalDateTime.now())
                .build();
    }

    public UserResponse userToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .bvn(user.getBvn())
                .email(user.getEmail())
                .build();
    }

    public boolean validatePassword(String password, String cpassword) {
        return password.equals(cpassword);
    }

    public void resendVerificationTokenMail(User user, String applicationUrl, VerificationToken verificationToken) {
        // send email to user
        String url = applicationUrl
                + "="
                + verificationToken.getToken();
        resendVerificationToken(user, url);
        log.info("Verification token: {} has been resend", url);
    }

    private void resendVerificationToken(User user, String url) {
        String subject = "Verify your account";
        String senderName = "Fintech App";
        String mailContent = "<p> Dear "+ user.getLastName() +", </p>";
        mailContent += "<p> Please click the link to verify your account, </p>";
        mailContent += "<h3><a href=\""+ url + "\"> VERIFY ACCOUNT</a></h3>";
        sendMail(user, subject, senderName, mailContent, mailSender);
    }

    public static void sendMail(User user, String subject, String senderName, String mailContent, JavaMailSender mailSender) {
        mailContent += "<p>Best regards <br/> Fintech team </p>";
        setMailCredentials(user, subject, senderName, mailContent, mailSender);
    }

    public static void setMailCredentials(User user, String subject, String senderName, String mailContent, JavaMailSender mailSender) {
        try{
            // send message
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom("almustaphatukur00@gmail.com",senderName);
            helper.setTo(user.getEmail());
            helper.setSubject(subject);
            helper.setText(mailContent, true);
            mailSender.send(message);
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public String applicationUrl(HttpServletRequest request) {
//        return TEMP_APP_URL;
        return "http://localhost:3000" +


//        return "http://"+request.getServerName() +
//                ":" +
//                request.getServerPort() +
                request.getContextPath();
    }

}
