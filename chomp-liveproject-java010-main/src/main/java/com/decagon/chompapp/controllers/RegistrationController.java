package com.decagon.chompapp.controllers;


import com.decagon.chompapp.dtos.SignUpDto;
import com.decagon.chompapp.services.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;

@RequiredArgsConstructor
@RestController
public class RegistrationController {
    private final RegistrationService registrationService;


    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody SignUpDto signUpDto, HttpServletRequest request) throws MalformedURLException {
        return registrationService.registerUser(signUpDto,request);
    }

    @PostMapping("/confirmRegistration")
    public ResponseEntity<String> confirmRegistration(@RequestParam(value = "token") String token) {
        return registrationService.confirmRegistration(token);

    }

    @PostMapping("/verifyEmail")
    public ResponseEntity<String> verifyRegistration(@RequestParam(value = "id") long id) throws MalformedURLException {
        return registrationService.verifyRegistration(id);


    }
}
