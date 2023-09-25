package com.decadev.money.way.service;

import com.decadev.money.way.dto.request.LoginRequest;
import com.decadev.money.way.dto.response.LoginResponse;
import com.decadev.money.way.exception.UserAccountDisabledException;
import com.decadev.money.way.exception.UserNotFoundException;

public interface AuthenticationService  {

    LoginResponse authenticateUser(LoginRequest request) throws UserNotFoundException, UserAccountDisabledException;
    String validateUserVerificationToken(String theToken);
}
