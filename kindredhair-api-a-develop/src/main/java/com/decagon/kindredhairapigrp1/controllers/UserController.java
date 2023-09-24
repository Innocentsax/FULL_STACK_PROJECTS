package com.decagon.kindredhairapigrp1.controllers;


import com.decagon.kindredhairapigrp1.DTO.UserRegistrationRequest;
import com.decagon.kindredhairapigrp1.services.UserService;
import com.decagon.kindredhairapigrp1.utils.api.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser( @RequestBody UserRegistrationRequest userRegistrationRequest)
            throws Exception {


        ApiResponse response = userService.registerUser(userRegistrationRequest.getUser(), userRegistrationRequest.getHairProfileAnswer());

        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));

    }
}
