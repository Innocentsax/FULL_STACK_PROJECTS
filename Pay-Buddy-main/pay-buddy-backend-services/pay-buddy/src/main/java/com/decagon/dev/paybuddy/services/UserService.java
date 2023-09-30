package com.decagon.dev.paybuddy.services;
import com.decagon.dev.paybuddy.dtos.requests.CreateUserRequest;
import com.decagon.dev.paybuddy.dtos.requests.ForgetPasswordRequest;
import com.decagon.dev.paybuddy.dtos.requests.LoginUserRequest;
import com.decagon.dev.paybuddy.dtos.requests.SocialLoginUserRequest;
import com.decagon.dev.paybuddy.dtos.responses.vtpass.response.data.SocialLoginResponse;
import com.decagon.dev.paybuddy.dtos.requests.ResetPasswordRequest;
import com.decagon.dev.paybuddy.restartifacts.BaseResponse;

public interface UserService {

    BaseResponse signUp(CreateUserRequest createUserRequest);
    BaseResponse confirmRegistration(String confirmationToken);

    BaseResponse login(LoginUserRequest request);
   SocialLoginResponse socialLogin(SocialLoginUserRequest request);

    BaseResponse  forgotPasswordRequest(ForgetPasswordRequest forgotPasswordRequest);

    BaseResponse resetPassword(ResetPasswordRequest request, String token);

//    BaseResponse verifyToken(VerifyTokenRequest verifyTokenRequest);

}

