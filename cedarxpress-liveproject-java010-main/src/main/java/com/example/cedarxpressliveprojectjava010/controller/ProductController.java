package com.example.cedarxpressliveprojectjava010.controller;

import com.example.cedarxpressliveprojectjava010.dto.ViewProductDto;
import com.example.cedarxpressliveprojectjava010.entity.Product;
import com.example.cedarxpressliveprojectjava010.service.CloudinaryService;
import com.example.cedarxpressliveprojectjava010.service.ProductService;
import com.example.cedarxpressliveprojectjava010.util.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ProductController {
    private final ProductService productService;
    private final CloudinaryService cloudinaryService;

    @GetMapping("/{id}")
    public ResponseEntity<ViewProductDto> getASingleProduct(@PathVariable("id") long id){
        return productService.getASingleProduct(id);

    }

    @GetMapping("/")
    public List<Product> fetchAllProductByFilteringAndSorting(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "keyword", defaultValue = AppConstants.DEFAULT_KEYWORD, required = false) String keyword){

        return  productService.fetchAllProducts(pageNo, pageSize, sortBy, keyword);
    }
}
