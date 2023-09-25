package com.decadev.money.way.controller;

import com.decadev.money.way.dto.request.ChangePasswordRequest;
import com.decadev.money.way.dto.request.ChangeTransactionPinRequest;
import com.decadev.money.way.dto.request.RegisterRequest;
import com.decadev.money.way.event.UserRegistrationEvent;
import com.decadev.money.way.exception.UserAlreadyExistsException;
import com.decadev.money.way.exception.UserNotFoundException;
import com.decadev.money.way.model.User;
import com.decadev.money.way.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000/")
@Slf4j
public class UserController {

    private final UserService userService;
    private final ApplicationEventPublisher publisher;

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody @Valid RegisterRequest request, HttpServletRequest servletRequest) throws UserAlreadyExistsException, UserNotFoundException, IOException {

        User user = userService.registerUser(request);
        publisher.publishEvent(new UserRegistrationEvent(user, applicationUrl(servletRequest)));
        return ResponseEntity.ok().body("Registration Complete check your email to activate your account");
    }
    public String applicationUrl(HttpServletRequest request){
        return "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
    }

    @PutMapping("/change-transaction-pin")
    @CrossOrigin("http://localhost:3000/")
    public ResponseEntity<String> changeTransactionPin(@RequestBody @Valid ChangeTransactionPinRequest request) throws UserNotFoundException {
            return ResponseEntity.ok().body(userService.changeTransactionPin(request));
    }

    @PutMapping("/change-password")
    @CrossOrigin("http://localhost:3000/")
    public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePasswordRequest changerPassword, Authentication auth) {
        return new ResponseEntity<>(userService.changePassword(changerPassword, auth), HttpStatus.OK);
    }

}
