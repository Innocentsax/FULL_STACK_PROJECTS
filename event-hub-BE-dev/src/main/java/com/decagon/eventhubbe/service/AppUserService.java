package com.decagon.eventhubbe.service;

import com.decagon.eventhubbe.dto.request.LoginRequest;
import com.decagon.eventhubbe.dto.request.RegistrationRequest;
import com.decagon.eventhubbe.dto.response.LoginResponse;
import com.decagon.eventhubbe.dto.response.RefreshTokenResponse;
import com.decagon.eventhubbe.dto.response.RegistrationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface AppUserService {
    RegistrationResponse registerUser(RegistrationRequest registrationRequest, String usertype,
                                             HttpServletRequest request);

    LoginResponse authenticate(LoginRequest loginRequest);
    String uploadProfilePicture(MultipartFile file);

    RefreshTokenResponse refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

}