package com.goCash.controller;

import com.goCash.dto.request.LoginRequest;
import com.goCash.dto.request.PasswordResetEmailRequest;
import com.goCash.dto.request.PasswordResetNewPasswordRequest;
import com.goCash.dto.request.UserRegistrationRequest;
import com.goCash.dto.response.LoginResponse;
import com.goCash.services.PasswordService;
import com.goCash.services.UserService;
import com.goCash.utils.ApiResponse;
import com.goCash.utils.validations.PasswordValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService appUserService;
    private final PasswordValidator passwordValidator;
    private final PasswordService passwordService;


    @PostMapping(path = "/register")
    public ResponseEntity<ApiResponse<?>> registerUser(@RequestBody @Valid UserRegistrationRequest request) {
        log.info("controller register: register user :: [{}] ::", request.getEmail());
        passwordValidator.isValid(request);
        ApiResponse<String> response = appUserService.registerUser(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping(path = "/login")
    public ResponseEntity<ApiResponse<LoginResponse>> loginUser(@RequestBody @Valid LoginRequest request) {
        log.info("request to login user");
        ApiResponse<LoginResponse> response = appUserService.login(request);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }
    @PostMapping("/password-reset-request")
    public ResponseEntity<ApiResponse<?>> resetPasswordRequest(@RequestBody PasswordResetEmailRequest emailRequest) throws MessagingException, UnsupportedEncodingException {
        String passwordResetUrl = passwordService.resetPasswordRequest(emailRequest);
        ApiResponse<Object> response = ApiResponse.builder()
                .code("00")
                .message("Password reset URL generated successfully")
                .data(passwordResetUrl)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<?>> resetPassword(@RequestBody PasswordResetNewPasswordRequest newPasswordRequest,
                                                        @RequestParam("token") String token) {
        String resetResult = passwordService.resetPassword(newPasswordRequest, token);
        ApiResponse<Object> response = ApiResponse.builder()
                .code("00")
                .message(resetResult)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



}