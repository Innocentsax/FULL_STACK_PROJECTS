package com.easyLend.userservice.services;

import com.easyLend.userservice.domain.constant.UserType;
import com.easyLend.userservice.request.LoginRequest;
import com.easyLend.userservice.request.RegisterRequest;
import com.easyLend.userservice.response.LoginResponse;
import com.easyLend.userservice.response.RegisterResponse;
import com.easyLend.userservice.response.UserResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface AppUserService {
    RegisterResponse registerUser(RegisterRequest request, UserType usertype, HttpServletRequest httpServletRequest);
    LoginResponse loginAuth(LoginRequest loginRequest);

    List<UserResponse> listOfUsers();


}
