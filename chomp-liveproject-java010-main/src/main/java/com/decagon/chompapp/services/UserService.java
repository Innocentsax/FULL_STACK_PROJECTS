package com.decagon.chompapp.services;

import com.decagon.chompapp.dtos.*;
import org.springframework.http.ResponseEntity;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


public interface UserService {

    ResponseEntity<String> editUserDetails(EditUserDto editUserDto);
    String generateResetToken(String email) throws MessagingException;

    ResponseEntity<String> enterResetPassword(String token, HttpServletResponse response);

    String resetPassword(ResetPasswordDto resetPasswordDto, String token);

    ResponseEntity<String> updatePassword(PasswordDto passwordDto);

    ResponseEntity<List<ProductDto>> viewAllFavoriteProduct();
    ResponseEntity<ProductDto> viewASingleFavoriteProduct( Long productId);
    ResponseEntity<UserDto> viewUserDetails();
}
