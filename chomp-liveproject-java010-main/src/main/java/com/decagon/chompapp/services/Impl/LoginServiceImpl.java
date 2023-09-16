package com.decagon.chompapp.services.Impl;


import com.decagon.chompapp.dtos.JwtAuthResponse;
import com.decagon.chompapp.dtos.LoginDto;
import com.decagon.chompapp.repositories.BlackListedTokenRepository;
import com.decagon.chompapp.security.JwtTokenProvider;
import com.decagon.chompapp.services.BlackListService;
import com.decagon.chompapp.services.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final HttpServletResponse httpServletResponse;
    private final BlackListService blackListService;
    private final BlackListedTokenRepository blackListedTokenRepository;
    private final HttpServletRequest httpServletRequest;



    @Override
    public ResponseEntity<JwtAuthResponse> login(LoginDto loginDto) throws Exception {
        Authentication authentication ;
        String token;

        try{
            Authentication auth =  new UsernamePasswordAuthenticationToken(
                    loginDto.getEmail(),loginDto.getPassword());

            authentication = authenticationManager.authenticate(auth);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenProvider.generateToken(authentication);
            httpServletResponse.setHeader("Authorization", token);

        }
        catch (
                BadCredentialsException ex){
            throw new Exception("incorrect user credentials", ex);

        }
        return ResponseEntity.ok(new JwtAuthResponse(token));
    }
    @Override
    public ResponseEntity<?> logout(String token) {

        token = httpServletRequest.getHeader("Authorization");

        blackListService.blackListToken(token);

        return new ResponseEntity<>("Logout Successful", HttpStatus.OK);

    }


}

