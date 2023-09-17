package com.decagon.safariwebstore.service;

import com.decagon.safariwebstore.dto.ProductDTO;
import com.decagon.safariwebstore.model.Product;
import com.decagon.safariwebstore.model.ProductPage;
import com.decagon.safariwebstore.payload.response.Response;
import com.decagon.safariwebstore.payload.response.auth.ResetPassword;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import javax.servlet.http.HttpServletRequest;

public interface AdminService {
    ResponseEntity<Response> adminForgotPassword(HttpServletRequest req, String email);
    ResponseEntity<Response> adminResetPassword(ResetPassword resetPassword);
    ResponseEntity<Page<ProductDTO>> getAllProducts(ProductPage adminProductPage);
    ResponseEntity<Product> getSingleProduct(Long productId);
}
