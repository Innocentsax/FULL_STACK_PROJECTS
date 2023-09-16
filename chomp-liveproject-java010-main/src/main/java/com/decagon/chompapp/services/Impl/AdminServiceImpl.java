package com.decagon.chompapp.services.Impl;

import com.decagon.chompapp.dtos.OrderResponse;
import com.decagon.chompapp.dtos.OrderResponseDto;
import com.decagon.chompapp.dtos.ProductDto;
import com.decagon.chompapp.exceptions.OrderNotFoundException;
import com.decagon.chompapp.exceptions.ProductNotFoundException;
import com.decagon.chompapp.models.Category;
import com.decagon.chompapp.models.Order;
import com.decagon.chompapp.models.Product;
import com.decagon.chompapp.models.ProductImage;
import com.decagon.chompapp.repositories.CategoryRepository;
import com.decagon.chompapp.repositories.OrderRepository;
import com.decagon.chompapp.repositories.ProductImageRepository;
import com.decagon.chompapp.repositories.ProductRepository;
import com.decagon.chompapp.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.decagon.chompapp.services.Impl.OrderServicesImpl.getOrderResponseDto;

@Service
public class AdminServiceImpl implements AdminService {
    private final CategoryRepository categoryRepository;

    private final ProductRepository productRepository;

    private final ProductImageRepository productImageRepository;

    private final OrderRepository orderRepository;


    Product product = new Product();


    @Autowired
    public AdminServiceImpl(CategoryRepository categoryRepository, ProductRepository productRepository, ProductImageRepository productImageRepository, OrderRepository orderRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.productImageRepository = productImageRepository;
        this.orderRepository = orderRepository;
    }


    @Override
    public ResponseEntity<String> createProduct(ProductDto productDto) {
        product = new Product();
        Optional<Category> productCategory = categoryRepository.findCategoryByCategoryName(productDto.getCategoryName().toLowerCase());
        Category category;
        if (productCategory.isEmpty()) {
            category = new Category();
            category.setCategoryName(productDto.getCategoryName());
            categoryRepository.save(category);
            product.setCategory(category);
        } else {
            category = productCategory.get();
            product.setCategory(category);
        }
        product.setProductName(productDto.getProductName());
        product.setProductPrice(productDto.getProductPrice());
        product.setSize(productDto.getSize());
        product.setQuantity(productDto.getQuantity());
        Product savedProduct = productRepository.save(product);

        return ResponseEntity.status(HttpStatus.CREATED).body("Product created successfully with product id " + savedProduct.getProductId());

    }


    @Override
    public ResponseEntity<ProductImage> saveProductImage(String productImageURL, Long productId) {
        ProductImage productImage = new ProductImage();
        productImage.setImageURL(productImageURL);
        Optional<Product> createdProduct = productRepository.findProductByProductId(productId);
        return getProductImageResponseEntity(productId, productImage, createdProduct);
    }


    @Override
    public ResponseEntity<String> updateProduct(ProductDto productDto, Long productId) {
        Product product = productRepository.findProductByProductId(productId).orElseThrow(() -> new ProductNotFoundException("Product","id", productId));
        product.setProductPrice(productDto.getProductPrice());
        product.setProductName(productDto.getProductName());
        product.setSize(productDto.getSize());
        product.setQuantity(productDto.getQuantity());

        Product updatedProduct = productRepository.save(product);

        return ResponseEntity.ok("Product updated successfully with product id " + updatedProduct.getProductId()) ;
    }

    @Override
    public ResponseEntity<ProductImage> updateProductImage(String productImageURL, Long productId) {
        Optional<ProductImage> productImage1 = productImageRepository.findById(productId);
        Optional<Product> productToBeUpdated = productRepository.findProductByProductId(productId);
        productImage1.get().setImageURL(productImageURL);
        return getProductImageResponseEntity(productId, productImage1.get(), productToBeUpdated);
    }

    private ResponseEntity<ProductImage> getProductImageResponseEntity(Long productId, ProductImage productImage, Optional<Product> productToBeUpdated) {
        if (productToBeUpdated.isPresent()) {
            productImage.setProduct(productToBeUpdated.get());
            product.setProductImage(productImage.getImageURL());
        } else {
            throw new ProductNotFoundException("productName", "productId", productId);
        }
        productToBeUpdated.get().setProductImage(productImage.getImageURL());
        productRepository.save(productToBeUpdated.get());
        return ResponseEntity.ok(productImageRepository.save(productImage));
    }

    @Override
    public ResponseEntity<String> deleteProduct(Long productId) {

        Optional<Product> product = productRepository.findProductByProductId(productId);
        if (!product.isPresent()) {
            throw new ProductNotFoundException("Product not found!");
        }
        productRepository.deleteById(productId);
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully");
    }

    @Override
    public ResponseEntity<OrderResponse> viewAllOrders(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Order> orders = orderRepository.findAll(pageable);

        List<Order> orderList = orders.getContent();
        List<OrderResponseDto> content =  orderList.stream().map(this::convertOrderEntityToOrderResponseDto).collect(Collectors.toList());
        OrderResponse orderResponse = OrderResponse.builder()
                .content(content)
                .pageNo(orders.getNumber())
                .pageSize(orders.getSize())
                .totalPages(orders.getTotalPages())
                .totalElements(orders.getNumberOfElements())
                .last(orders.isLast())
                .build();
            return ResponseEntity.ok(orderResponse);
    }
    private OrderResponseDto convertOrderEntityToOrderResponseDto(Order order) {
        return getOrderResponseDto(order);
    }

    @Override
    public ResponseEntity<OrderResponseDto> viewParticularOrder(long orderId) {
        Order order = orderRepository.findOrderByOrderId(orderId).orElseThrow(() -> new OrderNotFoundException("Order does not exist"));
        return ResponseEntity.ok(convertOrderEntityToOrderResponseDto(order));
    }
}
