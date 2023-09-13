package com.decagon.adire.service.implementation;

import com.decagon.adire.dto.request.ForgotPasswordDto;
import com.decagon.adire.dto.request.ResetPasswordRequest;
import com.decagon.adire.dto.response.MessageResponse;
import com.decagon.adire.entity.Designer;
import com.decagon.adire.exception.BadRequestException;
import com.decagon.adire.exception.NotFoundException;
import com.decagon.adire.mail.EmailService;
import com.decagon.adire.repository.DesignerRepository;
import com.decagon.adire.security.RandomGenerator;
import com.decagon.adire.service.AuthService;
import com.decagon.adire.service.DesignerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final DesignerRepository designerRepository;
    private final PasswordEncoder passwordEncoder;
    private final DesignerService userService;
    private final DesignerRepository userRepo;
    private final EmailService emailService;


    @Override
    public MessageResponse resetUserSecurityDetails(ResetPasswordRequest resetPasswordRequest) {

        String otp = resetPasswordRequest.getOtp();
        String confirmPassword = resetPasswordRequest.getConfirmPassword();

        Designer user = designerRepository.findByEmail(resetPasswordRequest.getEmail()).orElseThrow(() -> new BadRequestException("Email not found"));
        String validOtp = user.getOtp();
        Long currentTime = new Date().getTime();
        Long optCreatedAt = user.getOtpCreatedAt();

        if (validOtp == null || optCreatedAt == null) throw new NotFoundException("Otp not valid");

        if (!otp.equals(validOtp)) throw new BadRequestException("Otp not valid");

        long timeDiffInMSec = currentTime - optCreatedAt;
        long timeDiffInMin = timeDiffInMSec / 60000;

        if (timeDiffInMin < 5L) {
            user.setPassword(passwordEncoder.encode(confirmPassword));
            user.setOtp(null);
            user.setOtpCreatedAt(null);

            designerRepository.save(user);

        } else {
            throw new BadRequestException("Otp has expired");
        }

        return MessageResponse.builder()
                .message("Successfully reset security details")
                .build();
    }

    @Override
    public String passwordRequest(ForgotPasswordDto forgotPasswordDto) {
        log.info("sending a reset password email to the user");
        Designer designer = userService.findByEmail(forgotPasswordDto.getEmail());
        if (designer == null) {
            throw new NotFoundException("Email not found");
        }
        String otp = RandomGenerator.generateOtp();
        designer.setOtp(otp);
        designer.setOtpCreatedAt(new Date().getTime());
        userRepo.save(designer);
        try {
            log.info("Otp - > {}",otp);
            emailService.sendForgotPasswordEmail(designer, otp);
        } catch (Exception e) {
            log.info("Error sending forgot password email :  {} ", e.getMessage());
            e.printStackTrace();

        }
        log.info("email sent successfully");
        return "Reset password mail queued successfully";
    }
}
