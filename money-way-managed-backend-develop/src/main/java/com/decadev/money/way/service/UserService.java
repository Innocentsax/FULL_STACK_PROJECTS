package com.decadev.money.way.service;

import com.decadev.money.way.dto.request.ChangePasswordRequest;
import com.decadev.money.way.dto.request.ChangeTransactionPinRequest;
import com.decadev.money.way.dto.request.RegisterRequest;
import com.decadev.money.way.exception.UserAlreadyExistsException;
import com.decadev.money.way.exception.UserNotFoundException;
import com.decadev.money.way.model.User;
import org.springframework.security.core.Authentication;

import java.io.IOException;

public interface UserService {
    String changeTransactionPin(ChangeTransactionPinRequest request) throws UserNotFoundException;

    User registerUser(RegisterRequest request) throws UserAlreadyExistsException, UserNotFoundException, IOException;

    void saveUserVerificationToken(User user, String verificationToken);

    boolean verifyCurrentPassword(User user, String enteredPassword);

    String changePassword(ChangePasswordRequest req, Authentication auth);

}
