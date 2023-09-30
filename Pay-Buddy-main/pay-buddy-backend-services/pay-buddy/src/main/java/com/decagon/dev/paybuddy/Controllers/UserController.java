package com.decagon.dev.paybuddy.Controllers;

import com.decagon.dev.paybuddy.dtos.requests.CreateUserRequest;
import com.decagon.dev.paybuddy.dtos.requests.ForgetPasswordRequest;
import com.decagon.dev.paybuddy.dtos.requests.ResetPasswordRequest;
import com.decagon.dev.paybuddy.restartifacts.BaseResponse;
import com.decagon.dev.paybuddy.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RequestMapping("/api/v1/auth")
@AllArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public BaseResponse createUser(@RequestBody CreateUserRequest createUserRequest){
        return userService.signUp(createUserRequest);
    }
    @GetMapping("/confirmRegistration")
    public BaseResponse confirmRegistration(@RequestParam (name = "token") String token) {
        return userService.confirmRegistration(token);

    }

    @PostMapping("/forgot-password")
    public ResponseEntity<BaseResponse> forgotPassword(@RequestBody @Valid ForgetPasswordRequest request){
        return new ResponseEntity<>(userService.forgotPasswordRequest(request), HttpStatus.OK);
    }

    @PostMapping("/reset-password/{token}")
    public BaseResponse resetPassword(@RequestBody @Valid ResetPasswordRequest request, @PathVariable String token){
        return userService.resetPassword(request, token);
    }

//    @PostMapping("/verify-token")
//    public ResponseEntity<BaseResponse> verifyToken(@RequestBody @Valid VerifyTokenRequest verifyTokenRequest){
//        return ResponseEntity.ok(userService.verifyToken(verifyTokenRequest));
//    }


}
