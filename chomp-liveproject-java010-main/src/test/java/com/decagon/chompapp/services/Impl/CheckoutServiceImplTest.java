package com.decagon.chompapp.services.Impl;

import com.decagon.chompapp.dtos.OrderDto;
import com.decagon.chompapp.dtos.ProductDto;
import com.decagon.chompapp.dtos.ShippingAddressDto;
import com.decagon.chompapp.enums.OrderStatus;
import com.decagon.chompapp.enums.PaymentMethod;
import com.decagon.chompapp.enums.ShippingMethod;
import com.decagon.chompapp.exceptions.CartIsEmptyException;
import com.decagon.chompapp.models.*;
import com.decagon.chompapp.repositories.*;
import com.decagon.chompapp.services.CartService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CheckoutServiceImplTest {

    @Mock
    private  UserRepository userRepository;

    @Mock
    private  OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private ShippingAddressRepository shippingAddressRepository;

    @Mock
    private CartRepository cartRepository;


    @Mock
    private CartServiceImpl cartService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CheckoutServiceImpl checkoutService;

    ProductDto productDto;
    Product product;
    Category category;
    ProductImage productImage;
    User user;
    Cart userCart;
    CartItem cartItem, cartItem1;
    Order order;
    OrderItem orderItem;
    OrderDto orderDto;
    ShippingAddress shippingAddress;
    ShippingAddressDto shippingAddressDto;

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




        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void checkoutWithWrongUser() {
        Assertions.assertThrows(UsernameNotFoundException.class, () ->checkoutService.checkout(orderDto) );

    }

    @Test
    void checkoutWhenUserCartIsEmpty() {
        user = User.builder().userId(1L).email("ukeloveth247@gmail.com").firstName("Loveth").build();
        userCart = Cart.builder().cartId(1L).user(user).cartItemList(new ArrayList<>()).cartTotal(0).quantity(0).build();
        user.setCart(userCart);
        cartItem1 = CartItem.builder().cartItemId(1L).cart(userCart).product(product).quantity(0).subTotal(0).build();
        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn(user.getUsername());
        when(userRepository.findByUsernameOrEmail(any(), any())).thenReturn(Optional.of(user));
        Assertions.assertThrows(CartIsEmptyException.class, () ->checkoutService.checkout(orderDto) );

    }

    @Test
    void checkout() {

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

        order = Order.builder()
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


        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn(user.getUsername());
        when(userRepository.findByUsernameOrEmail(any(), any())).thenReturn(Optional.of(user));
        when(cartService.clearCart()).thenReturn(ResponseEntity.ok("Cart cleared successfully"));
        given(modelMapper.map(any(), eq(ShippingAddress.class))).willReturn(shippingAddress);
        when(shippingAddressRepository.save(any())).thenReturn(shippingAddress);
        when(orderItemRepository.save(any())).thenReturn(orderItem);
        when(orderRepository.save(any())).thenReturn(order);
        var ans = checkoutService.checkout(orderDto);
        Assertions.assertEquals(HttpStatus.OK, ans.getStatusCode());


    }
}