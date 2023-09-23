package com.decagon.fitnessoapp.service;

import com.decagon.fitnessoapp.dto.PersonResponse;
import com.decagon.fitnessoapp.model.user.Person;
import com.decagon.fitnessoapp.model.user.VerificationToken;

import java.util.Optional;

public interface VerificationService {

    String saveVerificationToken(Person person);

    Optional<VerificationToken> getToken(String token);

    void setConfirmedAt(String token);

    PersonResponse confirmToken(String token);
}
