package com.decagon.chompapp.services.Impl;

import com.decagon.chompapp.dtos.OrderDto;
import com.decagon.chompapp.dtos.OrderResponseDto;
import com.decagon.chompapp.dtos.ProductDto;
import com.decagon.chompapp.dtos.ShippingAddressDto;
import com.decagon.chompapp.enums.OrderStatus;
import com.decagon.chompapp.enums.PaymentMethod;
import com.decagon.chompapp.enums.ShippingMethod;
import com.decagon.chompapp.exceptions.ProductNotFoundException;
import com.decagon.chompapp.models.*;
import com.decagon.chompapp.repositories.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.atLeastOnce;

@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductImageRepository productImageRepository;

    @Mock
    OrderRepository orderRepository;

    @Mock
    CartRepository cartRepository;

    @Mock
    private ShippingAddressRepository shippingAddressRepository;

    @Mock
    private ModelMapper modelMapper;





    @InjectMocks
    private AdminServiceImpl adminService;

    ProductDto productDto;
    Product product;
    Category category;
    ProductImage productImage;
    Pageable pageable;

    Page<Order> orders;



    User user;
    Cart userCart;
    CartItem cartItem, cartItem1;
    Order newOrder;
    OrderItem orderItem;
    OrderDto orderDto;
    ShippingAddress shippingAddress;
    ShippingAddressDto shippingAddressDto;
    OrderResponseDto orderResponseDto;

    @BeforeEach
    void setUp() {

        productDto = ProductDto.builder()
                .productId(1L)
                .productPrice(200)
                .productName("Pizza")
                .categoryName("sides")
                .quantity(2L)
                .size("Big")
                .build();

        category = Category.builder()
                .categoryName("sides")
                .build();

        product = Product.builder()
                .productId(productDto.getProductId())
                .productName(productDto.getProductName())
                .quantity(productDto.getQuantity())
                .size(productDto.getSize())
                .productPrice(productDto.getProductPrice())
                .category(category)
                .build();

        productImage = ProductImage.builder()
                .productImageId(1L)
                .imageURL("http://res.cloudinary.com/deenn/image/upload/v1652303973/gcljvikqbxofsgkr11cl.png")
                .build();






    }

    @Test
    void createProduct() {

        when(categoryRepository.findCategoryByCategoryName(any())).thenReturn(Optional.of(category));
        when(productRepository.save(any())).thenReturn(product);
        ResponseEntity<String > savedProduct = adminService.createProduct(productDto);
        org.assertj.core.api.Assertions.assertThat(savedProduct.getBody()).isNotNull();

        Assertions.assertEquals(savedProduct.getBody(),   "Product created successfully with product id "+ product.getProductId());
        Assertions.assertEquals(savedProduct.getStatusCode(), HttpStatus.CREATED);

        verify(categoryRepository, atLeastOnce()).findCategoryByCategoryName(any());
        verify(productRepository, atLeastOnce()).save(any());


    }

    @Test
    void saveProductImage() {
        String url = "";
        when(productRepository.findProductByProductId(product.getProductId())).thenReturn(Optional.of(product));
        when(productImageRepository.save(any())).thenReturn(productImage);
        ResponseEntity<ProductImage> productImageResponseEntity = adminService.saveProductImage(url, product.getProductId());
        Assertions.assertEquals(productImageResponseEntity.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(productImageResponseEntity.getBody(), productImage);
        Assertions.assertEquals(productImageResponseEntity.getBody().getImageURL() , productImage.getImageURL());
        verify(productRepository, atLeastOnce()).findProductByProductId(any());
        verify(productImageRepository, atLeastOnce()).save(any());

    }

    @Test
    void updateProduct(){
        when(productRepository.findProductByProductId(any())).thenReturn(Optional.of(product));
        when(productRepository.save(any())).thenReturn(product);
        ResponseEntity<String> result = adminService.updateProduct(productDto,product.getProductId());

        Assertions.assertEquals(result.getBody(),"Product updated successfully with product id " + product.getProductId() );
        verify(productRepository).findProductByProductId(any());
    }

    @Test
    void updateProductImage(){
        when(productImageRepository.findById(any())).thenReturn(Optional.of(productImage));
        when(productRepository.findProductByProductId(any())).thenReturn(Optional.of(product));
        when(productRepository.save(any())).thenReturn(product);
        when(productImageRepository.save(any())).thenReturn(productImage);
        ResponseEntity<ProductImage> result = adminService.updateProductImage(product.getProductImage(), product.getProductId());
        Assertions.assertEquals(result.getBody(),productImage);
        verify(productImageRepository).save(any());
        verify(productRepository).save(any());
    }
    @Test
    void testForAdminToDeleteProduct() {
        doThrow(new ProductNotFoundException("Product is not found!")).when(this.productRepository).deleteById((Long) any());
        when(this.productRepository.findProductByProductId((Long) any())).thenReturn(Optional.of(product));
        assertThrows(ProductNotFoundException.class, () -> this.adminService.deleteProduct(123L));
        verify(this.productRepository).findProductByProductId((Long) any());
        verify(this.productRepository).deleteById((Long) any());
        when(this.productRepository.findProductByProductId((Long) any())).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> this.adminService.deleteProduct(123L));
    }

    @Test
    void viewParticularOrder() {

        user = User.builder().userId(1L).email("ukeloveth247@gmail.com").firstName("Loveth").build();
        userCart = Cart.builder().cartId(1L).user(user).cartItemList(new ArrayList<>()).cartTotal(0).quantity(0).build();
        user.setCart(userCart);
        cartItem = CartItem.builder().cartItemId(1L).cart(userCart).product(product).quantity(2).subTotal(product.getProductPrice() * 2).build();
        cartItem1 = CartItem.builder().cartItemId(1L).cart(userCart).product(product).quantity(2).subTotal(product.getProductPrice() * 2).build();
        List<CartItem> cartItemList = new ArrayList<>();
        cartItemList.add(cartItem);
        cartItemList.add(cartItem1);
        user.getCart().setCartItemList(cartItemList);
        user.getCart().setQuantity(2);
        user.getCart().setCartTotal(cartItem1.getSubTotal());
        cartRepository.save(userCart);
        orderItem=OrderItem.builder()
                .quantity(cartItem1.getQuantity())
                .product(cartItem1.getProduct())
                .subTotal(cartItem1.getSubTotal())
                .build();
        shippingAddressDto = ShippingAddressDto.builder()
                .city("djfhds")
                .email("jdf@gmail.com")
                .isDefaultShippingAddress(true)
                .build();
        orderDto = OrderDto.builder()
                .paymentType(PaymentMethod.PAY_WITH_CARD)
                .shippingMethod(ShippingMethod.FLAT_RATE)
                .shippingAddress(shippingAddressDto)
                .build();

        newOrder = Order.builder()
                .orderId(1L)
                .dateOrdered(new Date())
                .paymentMethod(orderDto.getPaymentType())
                .shippingAddress(shippingAddressRepository.save(modelMapper.map(orderDto.getShippingAddress(), ShippingAddress.class)))
                .status(OrderStatus.PENDING)
                .shippingMethod(orderDto.getShippingMethod())
                .user(user)
                .flatRate(userCart.getCartItemList().size() * 100.00)
                .totalPrice(userCart.getCartTotal())
                .orderItems(userCart.getCartItemList().stream().map(cartItem -> OrderItem.builder()
                        .quantity(cartItem.getQuantity())
                        .product(cartItem.getProduct())
                        .subTotal(cartItem.getSubTotal())
                        .build()
                ).collect(Collectors.toList()))
                .build();

        orderResponseDto = OrderResponseDto.builder()
                .orderId(newOrder.getOrderId())
                .subTotal(newOrder.getTotalPrice())
                .total(newOrder.getFlatRate() + newOrder.getTotalPrice())
                .dateOrdered(newOrder.getDateOrdered())
                .flatRate(newOrder.getFlatRate())
                .shippingAddress(newOrder.getShippingAddress())
                .status(newOrder.getStatus())
                .paymentMethod(newOrder.getPaymentMethod())
                .shippingMethod(newOrder.getShippingMethod())
                .productList(newOrder.getOrderItems().stream().map(orderItem -> Product.builder()
                        .productId(orderItem.getProduct().getProductId())
                        .size(orderItem.getProduct().getSize())
                        .productName(orderItem.getProduct().getProductName())
                        .productPrice(orderItem.getProduct().getProductPrice())
                        .productImage(orderItem.getProduct().getProductImage())
                        .createdDate(orderItem.getProduct().getCreatedDate())
                        .build()).collect(Collectors.toList()))
                .build();

        when(orderRepository.findOrderByOrderId(anyLong())).thenReturn(Optional.of(newOrder));
        ResponseEntity<OrderResponseDto> orderResponseDtoResponseEntity = adminService.viewParticularOrder(newOrder.getOrderId());
        org.assertj.core.api.Assertions.assertThat(orderResponseDtoResponseEntity).isNotNull();
        Assertions.assertEquals(orderResponseDtoResponseEntity.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(Objects.requireNonNull(orderResponseDtoResponseEntity.getBody()).getOrderId(), orderResponseDto.getOrderId());
        verify(orderRepository, times(1)).findOrderByOrderId(anyLong());
    }
}