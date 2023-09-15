package com.example.cedarxpressliveprojectjava010.controller;

import com.example.cedarxpressliveprojectjava010.dto.ProductDto;
import com.example.cedarxpressliveprojectjava010.entity.Product;
import com.example.cedarxpressliveprojectjava010.service.AdminService;
import com.example.cedarxpressliveprojectjava010.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/admin/")
@PreAuthorize("hasAuthority('ADMIN')")
@CrossOrigin("*")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private CloudinaryService cloudinaryService;

    @PostMapping("products")
    public ResponseEntity<Product> createProduct(@RequestBody ProductDto product) {
        return adminService.createProduct(product);
    }
    @PatchMapping("products")
    public ResponseEntity<Product> uploadImage(@RequestPart MultipartFile image, @RequestParam("id") Product product) {
        String url = cloudinaryService.uploadFile(image);
        Long productId = product.getId();
        return adminService.addProductImage(url,productId);
    }

    @DeleteMapping(value="/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long id) {
        return adminService.deleteProduct(id);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto, @PathVariable(name = "id") Long id) {
        ProductDto newProduct = adminService.updateProduct(productDto, id);
        return ResponseEntity.ok(newProduct);
    }
}