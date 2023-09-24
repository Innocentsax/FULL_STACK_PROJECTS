package com.decagon.kindredhairapigrp1.controllers;

import com.decagon.kindredhairapigrp1.services.Impl.HairEducationServiceImpl;
import com.decagon.kindredhairapigrp1.utils.api.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserHairEducationController {

    private HairEducationServiceImpl hairEducationService;

    UserHairEducationController(HairEducationServiceImpl hairEducationService){
        this.hairEducationService = hairEducationService;
    }

    @GetMapping("/user/{userDetailsID}/haireducation")
    ResponseEntity<ApiResponse<Object>> getUserHairEducation(@PathVariable Long userDetailsID){
        return hairEducationService.getUserHairEducation(userDetailsID);
    }

}