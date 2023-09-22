package com.decagon.fintechpaymentapisqd11a.services.serviceImpl;

import com.decagon.fintechpaymentapisqd11a.dto.MailServiceDto;
import com.decagon.fintechpaymentapisqd11a.dto.RegistrationRequestDto;
import com.decagon.fintechpaymentapisqd11a.exceptions.ConfirmationTokenException;
import com.decagon.fintechpaymentapisqd11a.exceptions.EmailNotValidException;
import com.decagon.fintechpaymentapisqd11a.exceptions.TokenNotFoundException;
import com.decagon.fintechpaymentapisqd11a.exceptions.UserNotFoundException;
import com.decagon.fintechpaymentapisqd11a.models.Users;
import com.decagon.fintechpaymentapisqd11a.repositories.UsersRepository;
import com.decagon.fintechpaymentapisqd11a.services.RegistrationService;
import com.decagon.fintechpaymentapisqd11a.token.ConfirmationToken;
import com.decagon.fintechpaymentapisqd11a.util.Constant;
import com.decagon.fintechpaymentapisqd11a.validations.EmailValidator;
import lombok.AllArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;


@Service
@AllArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
    private final UserServiceImpl userService;
    private final EmailValidator emailValidator;
    private final MailServiceImpl mailService;
    private final UsersRepository usersRepository;
    private final ConfirmationTokenServiceImpl confirmationTokenService;

    @Override
    public String createUser(RegistrationRequestDto registrationRequestDto) throws JSONException {
        boolean isValidEmail = emailValidator.test(registrationRequestDto.getEmail());
        if(!isValidEmail) {
            throw new EmailNotValidException("Email Not Valid");
        }
        String token = userService.registerUser(registrationRequestDto);

        String link = Constant.EMAIL_VERIFICATION_TOKEN_LINK + token;
         sendMail(registrationRequestDto.getFirstName(),
                 registrationRequestDto.getEmail(), link);
        return "Please check your email to verify your account";
    }

    @Override
    @Transactional
    public String confirmToken(String token) {

        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token)
                .orElseThrow(()-> new TokenNotFoundException("Token Not Found"));

        if (confirmationToken.getConfirmedAt() != null){
            throw new ConfirmationTokenException("Email Already Confirmed");
        }
        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
           Users users = usersRepository.findByEmail(confirmationToken.getUsers().getEmail())
                   .orElseThrow(()-> new UserNotFoundException("User Not Found"));
           resendEmail(users);
           return "Verification token expired. Check email for a new verification token";
        }
        confirmationTokenService.setConfirmedAt(token);
        userService.enableUser(
                confirmationToken.getUsers().getEmail());
        return "Confirmed";
    }

    @Override
    public void resendEmail(Users users) {
        String token = UUID.randomUUID().toString();
        String link = Constant.EMAIL_VERIFICATION_TOKEN_LINK + token;
        sendMail(users.getFirstName(),users.getEmail(), link);

        userService.saveToken(token, users);
    }

    @Override
    public void sendMail(String name, String email, String link){

        String subject = "Email Verification";
        String body = "Click the link below to verify your email \n " + link;
        MailServiceDto mailServiceDto = new MailServiceDto(name, email, body, subject);
        mailService.sendNotification(mailServiceDto);

    }


}
