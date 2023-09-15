package com.example.cedarxpressliveprojectjava010.service.impl;

import com.example.cedarxpressliveprojectjava010.entity.Product;
import com.example.cedarxpressliveprojectjava010.repository.ProductRepository;
import com.example.cedarxpressliveprojectjava010.service.ProductService;
import com.example.cedarxpressliveprojectjava010.dto.ViewProductDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper mapper;

    @Override
    public ResponseEntity<ViewProductDto> getASingleProduct(long id) {
        Product product = productRepository.findById(id).orElseThrow(RuntimeException::new);
        return new ResponseEntity<>(mapToDto(product), HttpStatus.OK);
    }

    @Override
    public List<Product> fetchAllProducts(int pageNo, int pageSize, String sortBy, String keyword) {

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Product> products = productRepository.findAll(keyword, pageable);
        List<Product> productList = products.getContent();

        return productList;

//        return productList.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private ViewProductDto mapToDto(Product product){
        return mapper.map(product, ViewProductDto.class);
    }
}
