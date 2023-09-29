package com.decagon.OakLandv1be.controllers;

import com.decagon.OakLandv1be.dto.NewProductRequestDto;
import com.decagon.OakLandv1be.dto.ProductCustResponseDto;
import com.decagon.OakLandv1be.dto.ProductResponseDto;
import com.decagon.OakLandv1be.entities.Product;


import com.decagon.OakLandv1be.services.ProductService;
import com.decagon.OakLandv1be.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/view/{product_id}")
    public ResponseEntity<ProductCustResponseDto> viewASingleProduct(@PathVariable("product_id") Long product_id){
        return new ResponseEntity<>(productService.fetchASingleProduct(product_id), HttpStatus.OK);
    }

    @GetMapping("/page-and-sort")
    public ResponseEntity<Page<ProductCustResponseDto>> productsByPaginationAndSorted(@RequestParam(defaultValue = "0") Integer pageNo,
                                                                                      @RequestParam(defaultValue = "10") Integer pageSize,
                                                                                      @RequestParam(defaultValue = "id") String sortBy,
                                                                                      @RequestParam(defaultValue = "false") boolean isAscending){
        return  new ResponseEntity<>(productService.productWithPaginationAndSorting(pageNo, pageSize, sortBy,isAscending),HttpStatus.OK);
    }

    @PostMapping("/upload-image/{productId}")
    public ResponseEntity<Object> uploadProfilePic(@RequestPart MultipartFile productImage, @PathVariable Long productId) throws IOException {
        return ResponseEntity.ok(productService.uploadProductImage(productId, productImage));
    }

    @GetMapping("/new-arrival")
    public ResponseEntity<List<ProductCustResponseDto>> viewNewArrivals(){
        return new ResponseEntity<>(productService.viewNewArrivalProducts(), HttpStatus.OK);
    }

    @GetMapping("/best-selling")
    public ResponseEntity<List<ProductCustResponseDto>> viewBestSelling(){
        return new ResponseEntity<>(productService.viewBestSellingProducts(), HttpStatus.OK);
    }

    @DeleteMapping("/delete-image")
    public ResponseEntity<String> deleteProductImage(String publicUrl){
        productService.deleteProductImage(publicUrl);
        return ResponseEntity.ok("Deleted");
    }

    @GetMapping("/paginated-all")
    public ApiResponse<Page<ProductResponseDto>> getAllProducts(@RequestParam(defaultValue = "0") Integer pageNo,
                                                                @RequestParam(defaultValue = "16") Integer pageSize,
                                                                @RequestParam(defaultValue = "id") String sortBy,
                                                                @RequestParam(defaultValue = "false") boolean isAscending) {
        return productService.getAllProducts(pageNo, pageSize, sortBy, isAscending);
    }

    @GetMapping("/subcategory/{subCategoryId}/paginated-all")
    public ApiResponse<Page<ProductResponseDto>> getAllProductsBySubCategory( @PathVariable Long subCategoryId,
                                                                 @RequestParam(defaultValue = "0") Integer pageNo,
                                                                 @RequestParam(defaultValue = "16") Integer pageSize,
                                                                 @RequestParam(defaultValue = "id") String sortBy,
                                                                 @RequestParam(defaultValue = "false") boolean isAscending) {
        return productService.getAllProductsBySubCategory(subCategoryId, pageNo, pageSize, sortBy, isAscending);
    }

    @PostMapping("/upload-image")
    public ResponseEntity<Object> uploadProfilePic(@RequestPart MultipartFile productImage) throws IOException {
        return ResponseEntity.ok(productService.uploadProductImageFileWithoutId(productImage));
    }

}