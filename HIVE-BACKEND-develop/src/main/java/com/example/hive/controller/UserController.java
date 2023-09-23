package com.example.hive.controller;

import com.example.hive.dto.request.ForgetPasswordDto;
import com.example.hive.dto.request.ResetPasswordDto;
import com.example.hive.dto.response.AppResponse;
import com.example.hive.entity.User;
import com.example.hive.exceptions.CustomException;
import com.example.hive.service.implementation.PasswordServiceImpl;
import com.example.hive.service.implementation.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final UserServiceImpl userService;
    private final PasswordServiceImpl passwordService;



    @PostMapping("/forget_password")
    @Operation(summary = "Forget password", responses = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AppResponse.class)))})
    public ResponseEntity<AppResponse<?>> forgetPassword(@RequestBody ForgetPasswordDto passwordDto,
                                                         HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
        Optional<User> user = userService.findUserByEmail(passwordDto.getEmail());
        String url = "";
        if(user.isPresent()) {
            String token = UUID.randomUUID().toString();
            passwordService.createPasswordResetTokenForUser(user.get(), token);
            url = passwordService.passwordResetTokenMail(user.get(), passwordService.applicationUrl(request), token);
        } else {
            throw new CustomException("User not found");
        }
        log.info("Sending a reset password link to {}", passwordDto.getEmail());
        log.info(url);
        passwordService.sendEmail(passwordDto.getEmail(), url);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/user/forget_password").toUriString());
        return ResponseEntity.created(uri).body(AppResponse.buildSuccess(url));
    }

    @PostMapping("/reset_Password")
    @Operation(summary = "Reset password", responses = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AppResponse.class)))})
    public ResponseEntity<AppResponse<?>> resetPassword(@RequestParam("token") String token, @RequestBody ResetPasswordDto passwordDto) {
        String result = passwordService.validatePasswordResetToken(token);

        if(!result.equalsIgnoreCase("valid")) {
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/user/reset_password").toUriString());
            return ResponseEntity.created(uri).body(AppResponse.buildSuccess("Invalid Token"));
        }

        Optional<User> user = userService.getUserByPasswordResetToken(token);
        if(user.isPresent()) {
            passwordService.changePassword(user.get(), passwordDto);
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/user/reset_password").toUriString());
            return ResponseEntity.created(uri).body(AppResponse.buildSuccess("Password reset successful"));
        } else {
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/user/reset_password").toUriString());
            return ResponseEntity.created(uri).body(AppResponse.buildSuccess("Invalid Token"));
        }
    }
}