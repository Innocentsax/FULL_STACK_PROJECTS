package com.decagon.kindredhairapigrp1.controllers;


import com.decagon.kindredhairapigrp1.DTO.AuthenticationRequest;
import com.decagon.kindredhairapigrp1.DTO.PasswordResetDTO;
import com.decagon.kindredhairapigrp1.DTO.RecommendationLinkDTO;
import com.decagon.kindredhairapigrp1.services.AuthenticationService;


import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping(path = "/auth")
public class AuthenticationController {

    private AuthenticationService authenticationService;


    AuthenticationController(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }


    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest){
        return authenticationService.login(authenticationRequest);
    }

    @PostMapping("/password/reset")
    public ResponseEntity<?>  createResetLink(@RequestBody PasswordResetDTO passwordResetDTO
                                              ){
        return authenticationService.createResetLink(passwordResetDTO);
    }

    @GetMapping("/password/reset/{jwt}")
    public ResponseEntity<?>  checkPasswordResetLink(@PathVariable String jwt){
        return authenticationService.checkPasswordResetLink(jwt);
    }

    @PostMapping("/recommendation")
    public ResponseEntity<?>  createRecommendationLink(@RequestBody RecommendationLinkDTO recommendationLinkDTO){
        return authenticationService.createRecommendationLink(recommendationLinkDTO);
    }
}