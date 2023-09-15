package com.example.cedarxpressliveprojectjava010.service;

import com.example.cedarxpressliveprojectjava010.dto.request.ForgotPasswordRequest;
import com.example.cedarxpressliveprojectjava010.dto.response.MessageResponse;
import com.example.cedarxpressliveprojectjava010.dto.request.ResetPasswordRequest;
import org.springframework.http.ResponseEntity;
import javax.mail.MessagingException;

public interface UserManagementService {

    ResponseEntity<MessageResponse> forgotPassword(ForgotPasswordRequest request) throws MessagingException;

    ResponseEntity<MessageResponse> resetPassword(String jwToken, ResetPasswordRequest request);
}
