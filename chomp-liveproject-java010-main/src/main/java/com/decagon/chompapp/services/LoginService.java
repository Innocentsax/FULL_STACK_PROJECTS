package com.decagon.chompapp.services;

import com.decagon.chompapp.dtos.JwtAuthResponse;
import com.decagon.chompapp.dtos.LoginDto;
import org.springframework.http.ResponseEntity;

public interface LoginService {
    ResponseEntity<JwtAuthResponse> login(LoginDto loginDto) throws Exception;
    ResponseEntity<?> logout(String token);


}
