package com.hrsupportcentresq014.controllers;

import com.hrsupportcentresq014.dtos.request.AuthenticationRequest;
import com.hrsupportcentresq014.dtos.response.AuthenticationResponse;
import com.hrsupportcentresq014.services.TokenService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthenticationController {
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@NonNull @RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(tokenService.authenticateUser(request));
    }
}
