package com.example.food.services;

import com.example.food.dto.*;
import com.example.food.pojos.CreateUserRequest;
import com.example.food.pojos.LoginResponseDto;
import com.example.food.restartifacts.BaseResponse;
import org.springframework.http.ResponseEntity;


public interface UserService {

    BaseResponse signUp(CreateUserRequest createUserRequest);
    ResponseEntity<LoginResponseDto> login(LoginRequestDto request);

    BaseResponse editUserDetails(EditUserDto editUserDto);

    BaseResponse requestPassword(PasswordResetRequestDto passwordResetRequest);

    BaseResponse resetPassword(PasswordResetDto passwordReset);
    BaseResponse confirmRegistration(ConfirmRegistrationRequestDto confirmRegistrationRequestDto);

    BaseResponse updatePassword (ChangePasswordDto passwordDto);
    UserDetailsDto getUserDetails(Long userId);

}
