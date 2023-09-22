package com.decagon.fintechpaymentapisqd11a.services;

import com.decagon.fintechpaymentapisqd11a.dto.RegistrationRequestDto;
import com.decagon.fintechpaymentapisqd11a.models.Users;
import com.decagon.fintechpaymentapisqd11a.response.UserResponse;
import org.springframework.boot.configurationprocessor.json.JSONException;

public interface UsersService {

    String registerUser(RegistrationRequestDto registrationRequestDto) throws JSONException;
    void enableUser(String email);
    void saveToken(String token, Users users);
    UserResponse getUser();
}
