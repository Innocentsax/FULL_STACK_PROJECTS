package com.decadev.money.way.controller;

import com.decadev.money.way.dto.request.ForgotPasswordRequest;
import com.decadev.money.way.dto.request.ResetPassword;
import com.decadev.money.way.service.ForgotPasswordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000/")
@Slf4j
@RequestMapping("/api/v1/password")

public class ForgotPasswordController {
    private final ForgotPasswordService forgotPasswordService;

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@Valid @RequestBody ForgotPasswordRequest email) {
        return forgotPasswordService.resetForgotPassword(email.getEmail());
    }

    @GetMapping("/reset-password")
    public RedirectView validToken(@RequestParam("token") String token, RedirectAttributes attributes){
        if(Boolean.TRUE.equals(forgotPasswordService.validToken(token).getBody())) {
            attributes.addAttribute("attribute-token", token);
            return new RedirectView("http://localhost:3000/resetpassword");
        }
        return null;
    }

    @PutMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam("token") String token, @RequestBody ResetPassword resetPassword){
        return forgotPasswordService.ResetPassword(token,resetPassword);
    }

}