package com.goCash.services;

import com.goCash.dto.request.ChangePasswordRequestDto;
import org.springframework.stereotype.Service;


import com.goCash.dto.request.LoginRequest;
import com.goCash.dto.request.UserRegistrationRequest;
import com.goCash.dto.response.UserResponse;
import com.goCash.utils.ApiResponse;
@Service
public interface UserService {

    ApiResponse login(LoginRequest loginDTO);
    ApiResponse<String> registerUser(UserRegistrationRequest request);

    ApiResponse<UserResponse> getUser();

    ApiResponse<ChangePasswordRequestDto> changePassword(ChangePasswordRequestDto requestDto);
}
