package com.wakacast.controllers;

import com.wakacast.configurations.security_configs.JwtTokenUtil;
import com.wakacast.dto.ChangePasswordDto;
import com.wakacast.responses.ResponseData;
import com.wakacast.services.ChangePasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.wakacast.controllers.UserController.response;

@RestController
@RequiredArgsConstructor
public class ChangePasswordController {
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final ChangePasswordService changePasswordService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/changePassword")
    public ResponseEntity<ResponseData> updatePassword(@Valid @RequestBody ChangePasswordDto changePasswordDto, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("Authorization").substring(7);
        String email = jwtTokenUtil.getUserEmailFromToken(token);
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, changePasswordDto.getOldPassword()));
        } catch (DisabledException e) {
            throw new DisabledException("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("INVALID_CREDENTIALS", e);
        }
        return response(HttpStatus.CREATED, changePasswordService.updatePassword(changePasswordDto, email));

    }
}
