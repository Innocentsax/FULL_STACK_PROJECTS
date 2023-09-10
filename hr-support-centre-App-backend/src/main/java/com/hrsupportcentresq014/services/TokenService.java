package com.hrsupportcentresq014.services;

import com.hrsupportcentresq014.dtos.request.AuthenticationRequest;
import com.hrsupportcentresq014.dtos.response.AuthenticationResponse;
import org.springframework.stereotype.Service;

@Service
public interface TokenService {
    AuthenticationResponse authenticateUser(AuthenticationRequest request);
}
