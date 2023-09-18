package com.example.decapay.controllers.auth;


import com.example.decapay.pojos.requestDtos.PasswordUpdateRequest;
import com.example.decapay.services.PasswordUpdateService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/auth")
public class UpdatePasswordController {
    private final PasswordUpdateService passwordUpdateService;

    @PostMapping("/update-password")
    public ResponseEntity<String> updatePassword(@Valid @RequestBody PasswordUpdateRequest passwordUpdateRequest){
        this.passwordUpdateService.updatePassword(passwordUpdateRequest);
        return ResponseEntity.ok("password updated successfully!");
    }
}
