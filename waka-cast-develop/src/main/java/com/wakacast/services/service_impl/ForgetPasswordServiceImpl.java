package com.wakacast.services.service_impl;

import com.wakacast.configurations.security_configs.JwtTokenUtil;
import com.wakacast.exceptions.UserWithEmailNotFound;
import com.wakacast.requests.EmailRequest;
import com.wakacast.dto.ResetPasswordDto;
import com.wakacast.global_constants.Constants;
import com.wakacast.repositories.UserRepository;
import com.wakacast.services.ForgetPasswordService;
import com.wakacast.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ForgetPasswordServiceImpl  implements ForgetPasswordService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService jwtUserDetailsService;
    private final EmailService emailService;


    public boolean generateResetToken(String email) throws IOException {
        UserDetails userDetails= jwtUserDetailsService.loadUserByUsername(email);
        if(userDetails.getUsername().isEmpty()){
            throw new UserWithEmailNotFound("USER NAME NOT FOUND");
        }
        String token = jwtTokenUtil.generateToken(userDetails);

        String subject = "Reset Password Link";
        EmailRequest mailDto= new EmailRequest();
        mailDto.setTo(email);
        mailDto.setSubject(subject);
        String resetPasswordUrl = Constants.BASE_URL + "resetPassword/" + token;
        mailDto.setBody("click on this link to generate your new password: \n" + resetPasswordUrl);
        emailService.sendEmail(mailDto);

        return true;
    }

    @Override
    public void resetPassword(ResetPasswordDto resetPasswordDto, String token) {
        String userEmail= jwtTokenUtil.getUserEmailFromToken(token);
        Optional<User> user = userRepository.findUserByEmail(userEmail);
        if(user.isPresent()){
            user.get().setPassword(passwordEncoder.encode(resetPasswordDto.getNewPassword()));
            userRepository.save(user.get());
        }else{
            throw new UserWithEmailNotFound("User not found");
        }
    }


}
