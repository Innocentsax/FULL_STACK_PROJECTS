package com.easyLend.userservice.controller;

import com.easyLend.userservice.response.ApiResponse;
import com.easyLend.userservice.services.VerificationEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/token")
public class PasswordController {
    private final VerificationEmailService verificationEmailService;
    @CrossOrigin("http://localhost:5173")
    @GetMapping("/resend-link")
    public ResponseEntity<ApiResponse<String>> forgotPassword(@RequestParam("token") String token){
        ApiResponse<String> apiResponse = new ApiResponse<>(verificationEmailService.forgotPassword(token));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
