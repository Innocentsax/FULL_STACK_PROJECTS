package com.decadev.money.way.controller;

import com.decadev.money.way.dto.request.LoginRequest;
import com.decadev.money.way.dto.request.RegisterRequest;
import com.decadev.money.way.dto.response.LoginResponse;
import com.decadev.money.way.event.UserRegistrationEvent;
import com.decadev.money.way.exception.UserAccountDisabledException;
import com.decadev.money.way.exception.UserAlreadyExistsException;
import com.decadev.money.way.exception.UserNotFoundException;
import com.decadev.money.way.model.User;
import com.decadev.money.way.service.AuthenticationService;
import com.decadev.money.way.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000/")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final LogoutHandler logoutHandler;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) throws UserNotFoundException, UserAccountDisabledException {
        return ResponseEntity.ok(authenticationService.authenticateUser(request));
    }

    @GetMapping("/verify-email")
    public RedirectView verifyEmail(@RequestParam("token") String token){
        String result = authenticationService.validateUserVerificationToken(token);
        if(result.equals("Verified")){
            return new RedirectView("http://localhost:3000/account-already-verified");
        }
        if(result.equals("Already verified")){
            return new RedirectView("http://localhost:3000/account-already-verified");
        }
        if(result.equals("Token already expired")){
            return new RedirectView("http://localhost:3000/verify-email-link-expired");
        }
        return null;
    }
    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
            logoutHandler.logout(request, response, authentication);
        // Redirect the user to the frontend URL for sign-in
        return ResponseEntity.ok().body("You have been logged out successfully.");
    }


}
