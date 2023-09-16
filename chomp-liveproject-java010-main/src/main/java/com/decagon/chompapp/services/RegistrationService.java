package com.decagon.chompapp.services;

import com.decagon.chompapp.dtos.SignUpDto;
import com.decagon.chompapp.dtos.SignUpDto;
import com.decagon.chompapp.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;

@Service
public interface RegistrationService {
    ResponseEntity<String> registerUser(SignUpDto signUpDto, HttpServletRequest request) throws MalformedURLException;

    ResponseEntity<String> verifyRegistration(User user, HttpServletRequest request) throws MalformedURLException;

    ResponseEntity<String> verifyRegistration(long id) throws MalformedURLException;

    ResponseEntity<String> confirmRegistration(String token);
}
