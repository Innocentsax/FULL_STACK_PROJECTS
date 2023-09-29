package com.decagon.OakLandv1be.services.serviceImpl;

import com.decagon.OakLandv1be.config.tokens.TokenService;
import com.decagon.OakLandv1be.dto.ForgotPasswordRequestDto;
import com.decagon.OakLandv1be.dto.PasswordResetDto;
import com.decagon.OakLandv1be.dto.UpdatePasswordDto;
import com.decagon.OakLandv1be.entities.Person;
import com.decagon.OakLandv1be.entities.Token;
import com.decagon.OakLandv1be.exceptions.InputMismatchException;
import com.decagon.OakLandv1be.exceptions.PasswordMisMatchException;
import com.decagon.OakLandv1be.exceptions.ResourceNotFoundException;
import com.decagon.OakLandv1be.exceptions.UserNotFoundException;
import com.decagon.OakLandv1be.repositries.PersonRepository;
import com.decagon.OakLandv1be.repositries.TokenRepository;
import com.decagon.OakLandv1be.services.PersonService;
import com.decagon.OakLandv1be.utils.ApiResponse;
import com.decagon.OakLandv1be.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.decagon.OakLandv1be.enums.TokenStatus.ACTIVE;
import static com.decagon.OakLandv1be.enums.TokenStatus.EXPIRED;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;
    private final TokenRepository tokenRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    private final JavaMailServiceImpl javaMailService;

    @Override
    public String forgotPasswordRequest(ForgotPasswordRequestDto requestDto) throws IOException {
        String email = requestDto.getEmail();
        Person person = personRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("This User does not exist."));
        String tokenGenerated = tokenService.generatePasswordResetToken(email);
        Token token = new Token();
        token.setToken(tokenGenerated);
        token.setTokenStatus(ACTIVE);
        token.setPerson(person);
        tokenRepository.save(token);

        javaMailService.sendMail(requestDto.getEmail(), "This is a request to reset your password",
                "This is your reset link which expires in 15 minutes: http://localhost:8080/request-reset/" + tokenGenerated);

        return "Kindly, check your email for password reset instructions!";
    }

    @Override
    public String resetPassword(String token, PasswordResetDto passwordResetDto) {

        Token tokenEntity = tokenRepository.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("This Token does not exist."));
        if (tokenEntity.getTokenStatus().equals(EXPIRED))
            throw new ResourceNotFoundException("The Token has expired");

        if (!passwordResetDto.getPassword().equals(passwordResetDto.getConfirmPassword()))
            throw new InputMismatchException("Password does not match.");

        Person person = tokenEntity.getPerson();
        person.setPassword(passwordEncoder.encode(passwordResetDto.getPassword()));
        personRepository.save(person);
        tokenEntity.setTokenStatus(EXPIRED);
        tokenRepository.save(tokenEntity);

        return "Password reset done successfully!";
    }

    @Override
    public ApiResponse<String> updatePassword(UpdatePasswordDto updatePasswordDto) {
        String email = UserUtil.extractEmailFromPrincipal()
                .orElseThrow(() -> new UserNotFoundException("user does not exist"));
            String oldPassword = updatePasswordDto.getOldPassword();
            String newPassword = updatePasswordDto.getNewPassword();
            String confirmNewPassword = updatePasswordDto.getConfirmNewPassword();

            Person person = personRepository.findByEmail(email)
                    .orElseThrow(() -> new UserNotFoundException("This user does not exist"));

            String dbPassword = person.getPassword();
            if (!passwordEncoder.matches(oldPassword, dbPassword))
                throw new PasswordMisMatchException("Passwords do not match!");
            if(!confirmNewPassword.equals(newPassword))
                updatePasswordDto.setConfirmNewPassword(newPassword);

            person.setPassword(passwordEncoder.encode(newPassword));
            personRepository.save(person);
            return new ApiResponse<>("Password updated successfully",true,null,HttpStatus.OK);
        }

    }

