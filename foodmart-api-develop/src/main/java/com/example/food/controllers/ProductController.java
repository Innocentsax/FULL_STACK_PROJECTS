package com.example.food.controllers;
import com.example.food.dto.UpdateProductDto;
import com.example.food.pojos.UpdatedProductResponse;
import com.example.food.dto.ProductDto;
import com.example.food.pojos.CreateProductResponse;
import com.example.food.pojos.PaginatedProductResponse;
import com.example.food.pojos.ProductResponse;
import com.example.food.pojos.ProductResponseDto;
import com.example.food.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@CrossOrigin("http://localhost:3001/")
public class ProductController {
    private final ProductService productService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add-new-product")
    public ResponseEntity<CreateProductResponse> addNewProduct (@Valid @RequestBody ProductDto productDto)  {
        CreateProductResponse productResponse = productService.addNewProduct(productDto);
        return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
    }

    @PutMapping("/updateProduct/{productId}")
    ResponseEntity<UpdatedProductResponse> updateProduct(@PathVariable Long productId, @RequestBody UpdateProductDto productDto){
        UpdatedProductResponse response = productService.updateProduct(productId,productDto);
        return  new ResponseEntity<>(response,HttpStatus.CREATED);
    }
    @GetMapping("/search")
    public ResponseEntity<PaginatedProductResponse> searchProduct(@RequestParam (required = false, defaultValue = "") String filter,
                                                       @RequestParam (required = false, defaultValue = "productName") String sortBy,
                                                       @RequestParam (required = false, defaultValue = "asc") String sortDirection,
                                                       @RequestParam (required = false, defaultValue = "0") int pageNumber,
                                                       @RequestParam (required = false, defaultValue = "10") int pageSize
    ) {
        var response = productService.searchProduct(filter,sortBy,sortDirection,pageNumber,pageSize);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/get-all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProductResponse> fetchAllProducts() {
        return new ResponseEntity<>(productService.fetchAllProducts(),HttpStatus.ACCEPTED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductResponseDto>fetchSingleProduct(@PathVariable("productId") Long productId){
        return ResponseEntity.ok(productService.fetchSingleProduct(productId));
    }
}
