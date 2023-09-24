package com.decagon.kindredhairapigrp1.controllers;

import com.decagon.kindredhairapigrp1.services.HairProfileQuestionService;
import com.decagon.kindredhairapigrp1.utils.api.response.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HairProfileQuestionController {

    @Autowired
    private HairProfileQuestionService haiProfileQuestionService;

    @GetMapping("/")
    public ResponseEntity<ApiResponse<Object>> getAllQuestions(){
        return haiProfileQuestionService.getAllQuestions();
    }


}