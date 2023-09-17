package com.decagon.safariwebstore.controller;

import com.decagon.safariwebstore.dto.ProductDTO;
import com.decagon.safariwebstore.model.Product;
import com.decagon.safariwebstore.model.ProductPage;
import com.decagon.safariwebstore.payload.request.ProductRequest;
import com.decagon.safariwebstore.payload.response.Response;
import com.decagon.safariwebstore.payload.response.auth.ResetPassword;
import com.decagon.safariwebstore.service.AdminService;
import com.decagon.safariwebstore.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/admin")
@AllArgsConstructor
public class  AdminController {

    private final AdminService adminService;

    private final ProductService productService;
  
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/password-forgot")
    @Secured("ADMIN")
    public ResponseEntity<Response> adminForgotPassword(@RequestParam("email") String email, HttpServletRequest req){
        return adminService.adminForgotPassword(req, email);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/password-reset")
    @Secured("ADMIN")
    public ResponseEntity<Response> adminResetPassword(@Valid @RequestBody ResetPassword resetPassword) {
        return adminService.adminResetPassword(resetPassword);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add-product")
    @Secured("ADMIN")
    public ResponseEntity<?> addProduct(@Valid @RequestBody ProductRequest productRequest){

        productService.saveProduct(productRequest);

        return new ResponseEntity<>(new Response(200,
                "Product saved successfully"), HttpStatus.OK);
    }

    @GetMapping("/products")
    @Secured("ADMIN")
    public ResponseEntity<Page<ProductDTO>> getAllProducts(ProductPage adminProductPage) {
        return adminService.getAllProducts(adminProductPage);
    }

    @GetMapping("/products/{id}")
    @Secured("ADMIN")
    public ResponseEntity<Product> getSingleProduct(@PathVariable(name = "id")Long productId) {
        return adminService.getSingleProduct(productId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update-product/{id}")
    @Secured("ADMIN")
    public ResponseEntity<?> updateProduct(@PathVariable("id") long productId, @Valid @RequestBody ProductRequest productRequest){
        productService.updateProduct(productId, productRequest);
        // log.info(product.toString());
        return new ResponseEntity<>(new Response(200,
                "Product updated successfully"), HttpStatus.OK);
    }
    
    @Secured("ADMIN")
    @DeleteMapping("/delete-product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable(name = "id") Long productId) {
        productService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

