package com.decagon.kindredhairapigrp1.services.Impl;

import com.decagon.kindredhairapigrp1.DTO.AuthenticationRequest;
import com.decagon.kindredhairapigrp1.DTO.AuthenticationResponse;
import com.decagon.kindredhairapigrp1.DTO.PasswordResetDTO;
import com.decagon.kindredhairapigrp1.DTO.RecommendationLinkDTO;
import com.decagon.kindredhairapigrp1.models.User;
import com.decagon.kindredhairapigrp1.services.AuthenticationService;
import com.decagon.kindredhairapigrp1.services.MailingService;
import com.decagon.kindredhairapigrp1.services.UserService;
import com.decagon.kindredhairapigrp1.utils.api.response.ApiResponse;
import com.decagon.kindredhairapigrp1.utils.security.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;
    private UserService userService;
    private MailingService mailingService;

    AuthenticationServiceImpl(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserService userService,
                              @Qualifier("mailGun") MailingService mailingService){

        this.authenticationManager =authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.mailingService = mailingService;
    }

    /**
     * Method handles user login it throws a UserNotFoundException
     * if the user email or password is inaccurate or sends a jwt token and user role if a user
     * details has been authenticated
     * @param authenticationRequest
     * @return
     * @throws Exception
     */
    public ResponseEntity<?> login(AuthenticationRequest authenticationRequest){
        ApiResponse response = new ApiResponse();
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
            );
        }catch (UsernameNotFoundException | BadCredentialsException e){
            response.setError(e.getMessage());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setMessage("Incorrect login credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(response);
        }catch (Exception e){
            response.setError(e.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(response);
        }

        try{
            final User user = userService.getUserByEmail(authenticationRequest.getEmail());
            final String  jwt = jwtUtil.generateLoginToken(user);
            response = new ApiResponse(HttpStatus.ACCEPTED);
            //convert the list of user role to a string and strips it of the square brackets
            String role = user.getRole();
            response.setData(new AuthenticationResponse(jwt,role, user.getId()));
            response.setMessage("Success");
            return ResponseEntity.status(HttpStatus.ACCEPTED.value()).body(response);
        } catch (Exception e){
            response.setError(e.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(response);

        }

    }

    /**
     * Method handles user password reset. Its takes in a user email  validates that the email exists,
     * Creates a password reset token, appends it to the  link provided  amd sends the password reset link
     * to the email address provided.
     * @param passwordResetDTO
     * @return
     */
    @Override
    public ResponseEntity<?>  createResetLink(PasswordResetDTO passwordResetDTO){
        ApiResponse response = new ApiResponse();
        try{
            userService.getUserByEmail(passwordResetDTO.getEmail());
        }catch (UsernameNotFoundException e){
            response.setError(e.getMessage());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setMessage("Email not found");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(response);
        }catch (Exception e){
            response.setError(e.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(response);
        }

        String jwt = jwtUtil.generateToken(passwordResetDTO.getEmail(), 10);
        String resetUrl = passwordResetDTO.getLink() + "/" + jwt;

           boolean isSent =  mailingService.sendPasswordResetLink(passwordResetDTO.getEmail() ,resetUrl);
            if(!isSent) {
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                response.setError("Error Sending Message");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(response);
            }
                response.setStatus(HttpStatus.OK.value());
                response.setMessage("Success, Message Sent");
                return ResponseEntity.ok(response);
    }

    /**
     * Method validates the token attached to the password reset token
     * @param jwt
     * @return
     */
    @Override
    public ResponseEntity<?>  checkPasswordResetLink(String jwt){
        ApiResponse response = new ApiResponse();
        try{
            jwtUtil.isTokenExpired(jwt);
        }catch (ExpiredJwtException e){
            response.setMessage("Expired Token");
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setError(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }

        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Success, Token is valid");
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> createRecommendationLink(RecommendationLinkDTO recommendationLinkDTO) {
        try{
            userService.getUserByEmail(recommendationLinkDTO.getEmail());
        }catch (UsernameNotFoundException e){
            var res = new ApiResponse<>(HttpStatus.UNAUTHORIZED);
            res.setMessage("Email not found");
            res.setError(e.getMessage());
            return new ResponseEntity<>(res, HttpStatus.valueOf(res.getStatus()));
        }

        String jwt = jwtUtil.generateToken(recommendationLinkDTO.getEmail(), 525600);
        String reccommendationUrl = recommendationLinkDTO.getLink() + "/" + jwt;

        boolean isSent =  mailingService.sendViewRecommendationLink(recommendationLinkDTO.getEmail() ,reccommendationUrl);
        if(!isSent) {
            var res = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR);
            res.setError("Error Sending Message");
            return new ResponseEntity<>(res, HttpStatus.valueOf(res.getStatus()));
        }

        var res = new ApiResponse<>(HttpStatus.OK);
        res.setMessage("Success, Message Sent");
        return new ResponseEntity<>(res, HttpStatus.valueOf(res.getStatus()));
    }
}
