package com.decagon.chompapp.controllers;

import com.decagon.chompapp.dtos.*;
import com.decagon.chompapp.dtos.EditUserDto;
import com.decagon.chompapp.dtos.ProductDto;
import com.decagon.chompapp.dtos.PasswordDto;
import com.decagon.chompapp.dtos.ProductResponse;
import com.decagon.chompapp.services.OrderServices;
import com.decagon.chompapp.services.ProductServices;
import com.decagon.chompapp.services.UserService;
import com.decagon.chompapp.utils.AppConstants;
import lombok.AllArgsConstructor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.decagon.chompapp.dtos.PasswordDto;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;


@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping( "/api/v1/auth/users")
public class UserController {

    private final UserService userService;

    private final ProductServices productServices;

    private final OrderServices orderServices;

    @PutMapping("/edit")
    public ResponseEntity<String> editUserDetails(@Valid @RequestBody EditUserDto editUserDto) {
        return userService.editUserDetails(editUserDto);
    }

    @GetMapping("/getAllProducts")
    public ResponseEntity<ProductResponse> getAllProducts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir,
            @RequestParam(value = "filterBy", defaultValue = AppConstants.DEFAULT_FILTER_BY_PARAMETER, required = false) String filterBy,
            @RequestParam(value = "filterParam", defaultValue = AppConstants.DEFAULT_FILTER_PARAMETER, required = false) String filterParam,
            @RequestParam(value = "productPriceStartRange", defaultValue = AppConstants.DEFAULT_PRODUCT_PRICE_START_RANGE, required = false) String productPriceStartRange,
            @RequestParam(value = "productPriceEndRange", defaultValue = AppConstants.DEFAULT_PRODUCT_PRICE_END_RANGE, required = false) String productPriceEndRange)
            throws ServletException {
        return productServices.getAllProducts(pageNo, pageSize, sortBy, sortDir, filterBy, filterParam,productPriceStartRange,productPriceEndRange);
    }

    @PutMapping("/password-update")
    public ResponseEntity<String> login(@RequestBody PasswordDto passwordDto) {
        return userService.updatePassword(passwordDto);
    }

    @GetMapping("/fetch-single-product/{id}")
    public ResponseEntity<ProductDto> fetchSingleProduct(@PathVariable Long id) {
        return productServices.fetchSingleProductById(id);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody EmailSenderDto emailSenderDto) throws MessagingException {
        return new ResponseEntity<>(userService.generateResetToken(emailSenderDto.getTo()), OK);
    }

    @GetMapping("/enter-password")
    public ResponseEntity<String> enterNewPassword(@RequestParam("token") String token, HttpServletResponse response) {
        return userService.enterResetPassword(token, response);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordDto resetPasswordDto, HttpServletRequest request) {
        return new ResponseEntity<>(userService.resetPassword(resetPasswordDto, request.getHeader("Authorization")), OK);
    }

    @GetMapping("/favorites")
    public ResponseEntity<List<ProductDto>> viewAllFavoriteProducts(){
        return userService.viewAllFavoriteProduct();
    }

    @GetMapping("/favorites/{productId}")
    public ResponseEntity<ProductDto> viewASingleFavoriteProduct(
            @PathVariable("productId") Long productId) {
        return userService.viewASingleFavoriteProduct(productId);
    }

    @GetMapping("/display-user-details")
        public ResponseEntity<UserDto> displayUserDetails(){
        return userService.viewUserDetails();
    }

    @GetMapping("/orders")
    public ResponseEntity<OrderResponse> viewOrderHistoryForAPremiumUser (
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY_ORDER, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
        return orderServices.viewOrderHistoryForAPremiumUser(pageNo,pageSize,sortBy,sortDir);
    }


}
