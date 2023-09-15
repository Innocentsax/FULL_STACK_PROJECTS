package com.example.cedarxpressliveprojectjava010.service;

import com.example.cedarxpressliveprojectjava010.dto.ViewProductDto;
import com.example.cedarxpressliveprojectjava010.entity.Product;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface ProductService {
    List<Product> fetchAllProducts(int pageNo, int pageSize, String sortBy, String keyword);
    ResponseEntity<ViewProductDto> getASingleProduct(long id);
}
