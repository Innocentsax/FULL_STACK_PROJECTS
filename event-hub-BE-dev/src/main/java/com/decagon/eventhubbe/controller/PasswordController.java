package com.decagon.eventhubbe.controller;

import com.decagon.eventhubbe.dto.request.ResetPasswordRequest;
import com.decagon.eventhubbe.dto.response.APIResponse;
import com.decagon.eventhubbe.service.PasswordService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/password")
@RequiredArgsConstructor
public class PasswordController {
    private final PasswordService passwordService;

    @GetMapping("/forgot-password")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<APIResponse<String>> forgotPassword(@RequestParam("email") String email,
                                                              HttpServletRequest request){
        APIResponse<String> apiResponse = new APIResponse<>(passwordService.forgotPassword(email,request));
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }
    @PostMapping("/reset")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<APIResponse<String>> resetPassword(@RequestBody ResetPasswordRequest request){
        APIResponse<String> apiResponse = new APIResponse<>(passwordService.resetPassword(request));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
