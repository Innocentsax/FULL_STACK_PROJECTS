package com.decagon.fitnessoapp.service.serviceImplementation;

import com.decagon.fitnessoapp.dto.PersonResponse;
import com.decagon.fitnessoapp.exception.CustomServiceExceptions;
import com.decagon.fitnessoapp.model.user.Person;
import com.decagon.fitnessoapp.model.user.VerificationToken;
import com.decagon.fitnessoapp.repository.PersonRepository;
import com.decagon.fitnessoapp.repository.VerificationTokenRepository;
import com.decagon.fitnessoapp.service.VerificationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
public class VerificationTokenServiceImpl implements VerificationService {

    private final VerificationTokenRepository verificationTokenRepository;
    public final PersonRepository personRepository;

    public String saveVerificationToken(Person person){
        final String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(
                token, LocalDateTime.now(), LocalDateTime.now().plusHours(24), person);
        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public Optional<VerificationToken> getToken(String token){
        return verificationTokenRepository.findByTokenCode(token);
    }

    public void setConfirmedAt(String token){
        verificationTokenRepository.findByTokenCode(token).ifPresent((confirm) ->
                        confirm.setConfirmedAt(LocalDateTime.now()));
    }

    @Transactional
    public PersonResponse confirmToken(String token){
        VerificationToken verificationToken = getToken(token)
                .orElseThrow(() -> new CustomServiceExceptions("token not found"));

        if(verificationToken.getConfirmedAt() != null){
            throw new CustomServiceExceptions("email already confirmed");
        }

        LocalDateTime expiresAt = verificationToken.getExpiresAt();

        if(expiresAt.isBefore(LocalDateTime.now())){
            throw new CustomServiceExceptions("token expired");
        }

        setConfirmedAt(token);
        personRepository.findByEmail(verificationToken.getPerson().getEmail()).ifPresent((person) ->
                person.setVerifyEmail(true));
        return PersonResponse.builder().message("Email verified").build();
    }
}
