package com.fintech.app.controller;

import com.fintech.app.model.User;
import com.fintech.app.model.VerificationToken;
import com.fintech.app.request.UserRequest;
import com.fintech.app.response.BaseResponse;
import com.fintech.app.response.TransactionHistoryResponse;
import com.fintech.app.response.UserResponse;
import com.fintech.app.service.UserService;
import com.fintech.app.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    private final Util utility;

    @PostMapping("/register")
    public BaseResponse<UserResponse> createUserAccount(@Valid @RequestBody UserRequest userRequest,
                                                        HttpServletRequest request) throws JSONException {
        try {
            return userService.createUserAccount(userRequest, request);
        } catch (Exception e) {
            return new BaseResponse<>(HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
    }

    @GetMapping("/verifyRegistration")
    public BaseResponse<?> validateRegistrationToken(@RequestParam("token") String token){
        boolean isValid = userService.validateRegistrationToken(token);
        return isValid ? new BaseResponse<>(HttpStatus.OK, "User verified successfully", null)
                : new BaseResponse<>(HttpStatus.BAD_REQUEST, "User verification failed", null);
    }

    @GetMapping("/resendVerificationToken")
    public BaseResponse<?> resendVerificationToken(@RequestParam("token") String oldToken, HttpServletRequest request){
        VerificationToken verificationToken = userService.generateNewToken(oldToken);
        User user = verificationToken.getUser();
        utility.resendVerificationTokenMail(user, utility.applicationUrl(request), verificationToken);
        return new BaseResponse<>(HttpStatus.OK, "verification link sent", null);
    }

    @GetMapping()
    public BaseResponse<UserResponse> getUserProfile(){
        return userService.getUser();
    }


    @GetMapping("/view-transaction-history")
    public BaseResponse<TransactionHistoryResponse> fetchTransactionHistory
            (@PathParam("page") Integer page,
             @PathParam("size") Integer size,
             @PathParam("sortBy") String sortBy) {

        return userService.getTransactionHistory(page, size, sortBy);
    }


}
