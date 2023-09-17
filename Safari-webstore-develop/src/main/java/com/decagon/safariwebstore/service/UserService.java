package com.decagon.safariwebstore.service;

import com.decagon.safariwebstore.dto.UserDetailsDTO;
import com.decagon.safariwebstore.model.User;
import com.decagon.safariwebstore.payload.request.auth.EditUser;
import com.decagon.safariwebstore.payload.request.UpdatePasswordRequest;
import com.decagon.safariwebstore.payload.request.auth.RegisterUser;
import com.decagon.safariwebstore.payload.response.Response;
import com.decagon.safariwebstore.payload.response.auth.ResetPassword;
import org.springframework.http.ResponseEntity;
import com.decagon.safariwebstore.payload.response.UserDTO;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
public interface UserService {
    User saveUser(User user);
    boolean existsByMail(String email);
    User registration(RegisterUser registerUser);
    Optional<User> findUserByResetToken(String resetToken);
    Optional<User> getUserByEmail(String email);
    void deactivateResetPasswordToken();
    User findUserByEmail(String email);
    ResponseEntity<Response> userForgotPassword(HttpServletRequest request, String accountEmail);
    ResponseEntity<Response> userResetPassword(ResetPassword requestPassword);
    UserDTO updateUser(EditUser user);
    boolean checkIfValidOldPassword(User user, UpdatePasswordRequest updatePasswordRequest);
    boolean changeUserPassword(User user, UpdatePasswordRequest updatePasswordRequest);
    User findUserById(Long userId);
    UserDTO getUserDetails();
}
