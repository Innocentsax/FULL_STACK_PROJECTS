package com.easyLend.userservice.controller;

import com.easyLend.userservice.domain.constant.UserType;
import com.easyLend.userservice.request.LoginRequest;
import com.easyLend.userservice.request.RegisterRequest;
import com.easyLend.userservice.response.ApiResponse;
import com.easyLend.userservice.response.LoginResponse;
import com.easyLend.userservice.response.RegisterResponse;
import com.easyLend.userservice.services.AppUserService;
import com.easyLend.userservice.services.serviceImpl.VerificationServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {
    private final AppUserService appUserService;
    private final VerificationServiceImpl verificationService;
    
    @CrossOrigin("http://localhost:5173")
    @PostMapping("/register/{usertype}")
    public ResponseEntity<ApiResponse<RegisterResponse>> registerUser (@Validated @RequestBody RegisterRequest registerRequest,
                                                                       @PathVariable UserType usertype, HttpServletRequest request) {
        ApiResponse<RegisterResponse> apiResponse = new ApiResponse<>(appUserService.registerUser(registerRequest, usertype, request));
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }
    @CrossOrigin("http://localhost:5173")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login (@Validated@RequestBody LoginRequest loginRequest) {
        ApiResponse<LoginResponse> apiResponse = new ApiResponse<>(appUserService.loginAuth(loginRequest));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @CrossOrigin("http://localhost:5173")
    @GetMapping("/new-verification")
    public ResponseEntity<ApiResponse<String>>  newLink(@RequestParam("email") String email,HttpServletRequest request) {
        ApiResponse<String> apiResponse = new ApiResponse<>(verificationService.sendNewVerificationLink(email,request));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @CrossOrigin("http://localhost:5173")
    @GetMapping("/verify-user")
    public ResponseEntity<ApiResponse<String>> verifyUser(@RequestParam("token") String token,HttpServletRequest request) {
        ApiResponse<String> apiResponse = new ApiResponse<>(verificationService.verifyUser(token,request));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }





}
