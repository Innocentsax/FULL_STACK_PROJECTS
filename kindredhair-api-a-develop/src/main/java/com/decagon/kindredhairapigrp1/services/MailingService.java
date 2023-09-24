package com.decagon.kindredhairapigrp1.services;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;



public interface MailingService {
    boolean sendPasswordResetLink(String email, String link);
    boolean sendViewRecommendationLink(String email, String link);

}
