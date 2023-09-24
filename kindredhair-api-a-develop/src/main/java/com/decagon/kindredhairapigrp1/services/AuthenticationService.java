package com.decagon.kindredhairapigrp1.services;

import com.decagon.kindredhairapigrp1.DTO.AuthenticationRequest;
import com.decagon.kindredhairapigrp1.DTO.PasswordResetDTO;
import com.decagon.kindredhairapigrp1.DTO.RecommendationLinkDTO;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService  {
    ResponseEntity<?> login(AuthenticationRequest authenticationRequest);
    ResponseEntity<?>  createResetLink(PasswordResetDTO passwordResetDTO);
    ResponseEntity<?>  checkPasswordResetLink(String jwt);
    ResponseEntity<?>  createRecommendationLink(RecommendationLinkDTO recommendationLinkDTO);
}
