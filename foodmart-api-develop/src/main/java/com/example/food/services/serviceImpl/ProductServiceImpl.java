package com.example.food.services.serviceImpl;

import com.example.food.Enum.ResponseCodeEnum;
import com.example.food.Enum.Role;
import com.example.food.dto.ProductDto;
import com.example.food.dto.UpdateProductDto;
import com.example.food.model.Category;
import com.example.food.model.Product;
import com.example.food.model.Users;
import com.example.food.pojos.*;
import com.example.food.repositories.CategoryRepository;
import com.example.food.repositories.ProductRepository;
import com.example.food.repositories.UserRepository;
import com.example.food.services.ProductService;
import com.example.food.util.ResponseCodeUtil;
import com.example.food.util.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final UserUtil userUtil;
    private final ResponseCodeUtil responseCodeUtil = new ResponseCodeUtil();
    @Override
    public PaginatedProductResponse searchProduct(String filter,String sortBy, String sortDirection, int pageNumber, int pageSize) {

        Sort sort = sortDirection
                .equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        log.info("Sort: " + sort + " pageRequest: " + pageRequest);

        Page<Product> products;
        if (filter.isBlank()) {
            products = productRepository.findAll(pageRequest);
            log.info("Filter is null or Empty. All Products: {}", products);
        } else {
            products = productRepository.findByProductNameContainingIgnoreCase(filter, pageRequest);
        }
        PaginatedProductResponse paginatedResponse = PaginatedProductResponse.builder()
                .productList(products)
                .build();
        log.info("Paginated Response generated. PaginatedResponse:{}", paginatedResponse);
        return responseCodeUtil.updateResponseData(paginatedResponse, ResponseCodeEnum.SUCCESS);
    }

    @Override
    public UpdatedProductResponse updateProduct(Long productId, UpdateProductDto productDto) {
        UpdatedProductResponse response = new UpdatedProductResponse();
        String email = userUtil.getAuthenticatedUserEmail();
        Users user = userRepository.findByEmail(email).get();

        if (!user.getRole().equals(Role.ROLE_ADMIN)) {
            return responseCodeUtil.updateResponseData(response, ResponseCodeEnum.UNAUTHORISED_ACCESS);

        }
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            return responseCodeUtil.updateResponseData(response, ResponseCodeEnum.ERROR, "Product does not exist");
        }

        product.setProductName(productDto.getProductName());
        product.setProductPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        productRepository.save(product);

        return responseCodeUtil.updateResponseData(response, ResponseCodeEnum.SUCCESS, product.getProductName() + " updated successfully");
    }


    public CreateProductResponse addNewProduct(ProductDto productDto) {

        CreateProductResponse createProductResponse = CreateProductResponse.builder()
                .productName(productDto.getProductName())
                .build();
        Category category = categoryRepository.findByCategoryName(productDto.getCategoryName());
        if(category == null) {
            return responseCodeUtil.updateResponseData(createProductResponse, ResponseCodeEnum.ERROR,  "There is no Category by the name " + productDto.getCategoryName());
        }
        Optional<Product> newProduct = productRepository.findByProductName(productDto.getProductName());
        if (newProduct.isPresent()) {
            return responseCodeUtil.updateResponseData(createProductResponse, ResponseCodeEnum.ERROR, newProduct.get().getProductName() + " Already Exists!");
        }
        Product product = Product.builder()
                .category(category)
                .productPrice(productDto.getProductPrice())
                .productName(productDto.getProductName())
                .description(productDto.getDescription())
                .imageUrl(productDto.getImageUrl())
                .quantity(productDto.getQuantity())
                .build();
        productRepository.save(product);
        return responseCodeUtil.updateResponseData(createProductResponse, ResponseCodeEnum.SUCCESS, product.getProductName() + " Has Been Added");
    }
    

    public ProductResponse fetchAllProducts() {
        ProductResponse response = new ProductResponse();

        List<Product> products = productRepository.findAll();

        if (products.isEmpty()) {
            return responseCodeUtil.updateResponseData(response, ResponseCodeEnum.PRODUCT_NOT_FOUND);
        }

        List<ProductDto> productDtoList = products.stream()
                .map(this::productToProductDto).collect(Collectors.toList());

        response.setProductDto(productDtoList);
        return responseCodeUtil.updateResponseData(response, ResponseCodeEnum.SUCCESS);
    }

    private ProductDto productToProductDto(Product product) {
        return FavouritesServiceImpl.productToProductDto(product);
    }

    public ProductResponseDto fetchSingleProduct(Long productId) {

        ProductResponseDto responseDto = new ProductResponseDto();
        Optional<Product> fetchedProduct = productRepository.findById(productId);

        if (fetchedProduct.isEmpty()) {
            return responseCodeUtil.updateResponseData(responseDto,
                    ResponseCodeEnum.PRODUCT_NOT_FOUND, "Product not found for ID:" + productId);
        }
        Product product = fetchedProduct.get();
        log.info("response object {}", product);

        responseDto.setProductDto(productToProductDto(product));

        return responseCodeUtil.updateResponseData(responseDto, ResponseCodeEnum.SUCCESS);
    }

}
