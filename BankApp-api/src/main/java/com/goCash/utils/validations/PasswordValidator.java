package com.goCash.utils.validations;

import com.goCash.dto.request.UserRegistrationRequest;
import com.goCash.exception.PasswordMatcherException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PasswordValidator {
    public Boolean isValid(UserRegistrationRequest userRegistrationRequestDto) {
        String password = userRegistrationRequestDto.getPassword();
        String confirmPassword = userRegistrationRequestDto.getConfirmPassword();

        if (Objects.equals(password, confirmPassword)) {
            return true;
        } else {
            throw new PasswordMatcherException("password do not match", HttpStatus.BAD_REQUEST);
        }
    }
}
