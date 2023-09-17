package com.wakacast.controllers;

import com.wakacast.configurations.security_configs.JwtTokenUtil;
import com.wakacast.dto.UserDto;
import com.wakacast.enums.UserType;
import com.wakacast.exceptions.UserWithEmailNotFound;
import com.wakacast.global_constants.Constants;
import com.wakacast.models.User;
import com.wakacast.repositories.UserRepository;
import com.wakacast.requests.JwtRequest;
import com.wakacast.responses.JwtResponse;
import com.wakacast.services.AdminService;
import com.wakacast.services.service_impl.JwtUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class  JwtAuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final AdminService adminService;
    private final ModelMapper mapper;

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/login")
    public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest jwtRequest) throws IOException {
        authenticateUser(jwtRequest, authenticationManager);
        User user = userRepository.findUserByEmail(jwtRequest.getEmail()).orElseThrow(()->
                new UserWithEmailNotFound("User not found"));
        if (user.getUserType().equals(UserType.ADMIN)) {
            return adminService.generateLoginToken(jwtRequest.getEmail());
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getEmail());

        return generateJWTToken(userDetails);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/admin/confirm-token")
    public ResponseEntity<JwtResponse> confirmLoginToken(@RequestParam String loginToken) {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(adminService.confirmLoginToken(loginToken));

        return generateJWTToken(userDetails);
    }

    private ResponseEntity<JwtResponse> generateJWTToken(UserDetails userDetails) {
        final String jwtToken = jwtTokenUtil.generateToken(userDetails);
        final User user = userRepository.findUserByEmail(userDetails.getUsername()).orElseThrow(()->
                new UserWithEmailNotFound(Constants.USER_NOT_FOUND));
        UserDto userDto = mapper.map(user, UserDto.class);
        return ResponseEntity.ok(new JwtResponse(userDto, jwtToken));
    }

    private void authenticateUser(@RequestBody JwtRequest jwtRequest, AuthenticationManager authenticationManager) {
        Authentication authenticate;
        try {
            authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                    (jwtRequest.getEmail(), jwtRequest.getPassword()));
        } catch (DisabledException e) {
            throw new DisabledException("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("INVALID_CREDENTIALS", e);
        }
        SecurityContextHolder.getContext().setAuthentication(authenticate);
    }

}
