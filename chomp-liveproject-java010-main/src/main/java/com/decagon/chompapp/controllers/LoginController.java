package com.decagon.chompapp.controllers;


import com.decagon.chompapp.dtos.JwtAuthResponse;
import com.decagon.chompapp.dtos.LoginDto;
import com.decagon.chompapp.dtos.PasswordDto;
import com.decagon.chompapp.services.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto) throws Exception {

        return loginService.login(loginDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(String token) {

        return loginService.logout(token);
    }
}
