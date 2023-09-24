package com.decagon.kindredhairapigrp1.controllers;

import com.decagon.kindredhairapigrp1.DTO.AnswerResponseDTO;
import com.decagon.kindredhairapigrp1.DTO.ProductDTO;
import com.decagon.kindredhairapigrp1.services.UserService;
import com.decagon.kindredhairapigrp1.utils.api.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class UserProfileController {
    @Autowired
    UserService userService;

    @GetMapping("/user/{id}/profile")
    public ResponseEntity<ApiResponse<Object>> getUserProfileDetails(@PathVariable Long id){
       return userService.getUserProfileDetails(id);
    }

    @GetMapping("/user/{userId}/recommendation")
    public ResponseEntity<ApiResponse<Map<String, List<ProductDTO>>>> getUserRecommendedProduct(@PathVariable Long userId) {

        ApiResponse<Map<String, List<ProductDTO>>> response = userService.getUserRecommendedProduct(userId);
        return new ResponseEntity<>(response, HttpStatus.resolve(response.getStatus()));
    }

    @PutMapping("/user/{userId}/profile/update")
    public ResponseEntity<ApiResponse<Object>> updateUserDetails(@RequestBody AnswerResponseDTO answerResponseDTO, @PathVariable Long userId)
            throws Exception {
        ApiResponse<Object> response =  userService.updateUserRecommendation(userId , answerResponseDTO);
        return new ResponseEntity<>(response, HttpStatus.resolve(response.getStatus()));
    }
}
