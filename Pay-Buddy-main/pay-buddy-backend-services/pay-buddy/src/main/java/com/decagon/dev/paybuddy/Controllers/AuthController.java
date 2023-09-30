package com.decagon.dev.paybuddy.Controllers;

import com.decagon.dev.paybuddy.dtos.requests.LoginUserRequest;
import com.decagon.dev.paybuddy.dtos.requests.SocialLoginUserRequest;
import com.decagon.dev.paybuddy.dtos.responses.vtpass.response.data.SocialLoginResponse;
import com.decagon.dev.paybuddy.restartifacts.BaseResponse;
import com.decagon.dev.paybuddy.services.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserService userService;
    @PostMapping("/login")
    public BaseResponse login(@RequestBody LoginUserRequest request) {
        return userService.login(request);
    }

    @PostMapping("/social-login")
    public SocialLoginResponse socialLogin(@RequestBody  SocialLoginUserRequest request){
        return userService.socialLogin(request);
    }
}
