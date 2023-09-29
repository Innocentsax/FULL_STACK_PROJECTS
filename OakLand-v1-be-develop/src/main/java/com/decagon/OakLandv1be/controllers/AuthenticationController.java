package com.decagon.OakLandv1be.controllers;

import com.decagon.OakLandv1be.config.tokens.TokenService;
import com.decagon.OakLandv1be.config.userDetails.AppUserDetailsService;
import com.decagon.OakLandv1be.dto.ForgotPasswordRequestDto;
import com.decagon.OakLandv1be.dto.LoginDto;
import com.decagon.OakLandv1be.dto.PasswordResetDto;
import com.decagon.OakLandv1be.entities.Person;
import com.decagon.OakLandv1be.exceptions.InvalidAttributeException;
import com.decagon.OakLandv1be.repositries.PersonRepository;
import com.decagon.OakLandv1be.dto.UpdatePasswordDto;
import com.decagon.OakLandv1be.services.serviceImpl.PersonServiceImpl;

import com.decagon.OakLandv1be.services.PersonService;
import com.decagon.OakLandv1be.services.serviceImpl.PersonServiceImpl;
import com.decagon.OakLandv1be.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final AppUserDetailsService userDetailsService;

    private final PersonService personService;
    

    @PostMapping("/login")
        public ApiResponse authenticate(@Valid @RequestBody LoginDto loginRequest) {
            try {
                UserDetails user = userDetailsService.loadUserByUsername(loginRequest.getEmail());
                if(!user.isEnabled())
                    throw new UsernameNotFoundException("You have not been verified. Check your email to be verified!");
                if (!user.isAccountNonLocked()){
                    return new ApiResponse<>("This account has been deactivated", false, null,HttpStatus.OK);
                }
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
                if(authentication == null)
                    throw new InvalidCredentialsException("Invalid Email or Password");
                return new ApiResponse<>("Login Successful",
                        true, tokenService.generateToken(authentication), HttpStatus.OK);
            } catch (InvalidCredentialsException e) {
                return new ApiResponse<>("Invalid Credentials", false, null, HttpStatus.UNAUTHORIZED);
            } catch (BadCredentialsException | UsernameNotFoundException e) {
                return new ApiResponse<>("Password or Email not correct", false, null, HttpStatus.UNAUTHORIZED);
            }
    }

    @PostMapping("/forgot-password-request")
    public ResponseEntity<String> passwordRequestReset(@Valid @RequestBody ForgotPasswordRequestDto requestDto) throws IOException {
        String result = personService.forgotPasswordRequest(requestDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/reset-password/{token}")
    public ResponseEntity<String> passwordReset(@PathVariable String token, @Valid @RequestBody PasswordResetDto passwordResetDto){
        String result = personService.resetPassword(token, passwordResetDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/update-password")
    public ResponseEntity<ApiResponse<String>> updatePassword(@Valid  @RequestBody UpdatePasswordDto updatePasswordDto){
        ApiResponse response = personService.updatePassword( updatePasswordDto);
        return new ResponseEntity<>(response,HttpStatus.ACCEPTED);
    }
}
