package com.decagon.fintechpaymentapisqd11a.services;

import com.decagon.fintechpaymentapisqd11a.dto.RegistrationRequestDto;
import com.decagon.fintechpaymentapisqd11a.models.Users;
import org.springframework.boot.configurationprocessor.json.JSONException;

public interface RegistrationService {

    String createUser(RegistrationRequestDto registrationRequestDto) throws JSONException;

    String confirmToken(String token);

    void resendEmail(Users users);

    void sendMail(String name, String email, String link);
}
