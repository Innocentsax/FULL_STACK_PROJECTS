package com.hrsupportcentresq014.controllers;

import com.hrsupportcentresq014.dtos.request.PasswordResetRequest;
import com.hrsupportcentresq014.services.PasswordResetRequestService;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/password")
public class PasswordResetRequestController {
    private final PasswordResetRequestService resetRequestService;

    public PasswordResetRequestController(PasswordResetRequestService resetRequestService) {
        this.resetRequestService = resetRequestService;
    }

    @PostMapping("/forgot-password/")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordResetRequest email) throws MessagingException {
        return resetRequestService.resetPassword(email.getEmail());
    }

    @PostMapping("/password-reset-confirmation")
    public ResponseEntity<String> confirmPasswordReset(@RequestParam("resetToken") String resetToken,
                                                       @RequestBody PasswordResetRequest newPassword){
        return resetRequestService.completePasswordReset(resetToken, newPassword.getPassword());
    }
}
