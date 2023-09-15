package com.example.cedarxpressliveprojectjava010.service.impl;

import com.example.cedarxpressliveprojectjava010.dto.CheckOutDto;
import com.example.cedarxpressliveprojectjava010.dto.OrderDto;
import com.example.cedarxpressliveprojectjava010.entity.*;
import com.example.cedarxpressliveprojectjava010.enums.DeliveryStatus;
import com.example.cedarxpressliveprojectjava010.enums.Payment;
import com.example.cedarxpressliveprojectjava010.exception.CartEmptyException;
import com.example.cedarxpressliveprojectjava010.repository.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CheckOutServiceImplTest {
    @InjectMocks
    private CheckOutServiceImpl checkOutService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderItemRepository orderItemRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AddressRepository addressRepository;
    @Mock private CartRepository cartRepository;


    Cart cart2;
    User user2;
    CheckOutDto checkOutException;
    List<CartItem> cartItemList2 = new ArrayList<>();

    Cart cart;
    User user;
    Address address;
    Order order;
    CartItem cartItem;
    OrderItem orderItem;
    CheckOutDto checkOutDto;
    Product product;
    OrderDto orderDto;
    List<CartItem> cartItemList = new ArrayList<>();
    List<OrderItem> orderItemList = new ArrayList<>();

    Authentication authentication = Mockito.mock(Authentication.class);
    SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    Authentication auth = Mockito.mock(Authentication.class);

    @BeforeEach
    void setup(){
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);
        user = User.builder().firstName("Darlington").email("email").build();
        user2 = User.builder().firstName("Darlington").email("email").build();
        address = Address.builder().address("Edo").user(user).zipCode(898).build();
        address.setId(1L);
        cart = Cart.builder().customer(user).build();
        product = Product.builder().productName("Office Chair").price(200.00).build();
        cartItem = CartItem.builder().cart(cart).product(product).unit(0).build();
        cartItemList.add(cartItem);
        cartItemList.add(cartItem);
        cart.setCartItems(cartItemList);
        cart2 = Cart.builder().build();
        cart2.setCartItems(cartItemList2);


        checkOutDto = CheckOutDto.builder()
                .address_id(1L)
                .payment("CARD")
                .build();

        checkOutException = CheckOutDto.builder().build();
        orderItem = OrderItem.builder()
                .product(product)
                .build();
        orderItemList.add(orderItem);
        orderItemList.add(orderItem);
        order = Order.builder()
                .paymentMethod(Payment.CARD)
                .price(400.00)
                .timeOfCompletion(null)
                .deliveryStatus(DeliveryStatus.PENDING)
                .address(address).build();

        orderDto = OrderDto.builder()
                .deliveryStatus(DeliveryStatus.PENDING)
                .address(address.getAddress())
                .paymentMethod(Payment.CARD)
                .price(400.00)
                .user(user)
                .customerOrder(orderItemList)
                .timeOfCompletion(null)
                .build();
    }

    @DisplayName("Test to make an Order from a customer Cart")
    @Test
    void makeOrderUsingCheckOutOneDto() throws JsonProcessingException {

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn(user.getEmail());
        given(userRepository.getUserByEmail(user.getEmail())).willReturn(user);
        given(cartRepository.findCartByCustomer(user)).willReturn(Optional.of(cart));
        given(addressRepository.getById(checkOutDto.getAddress_id())).willReturn(address);
        given(orderRepository.save(any())).willReturn(order);

        ResponseEntity<OrderDto> responseEntity = checkOutService.makeOrder(checkOutDto);

        assertThat(responseEntity.getBody().getAddress()).isEqualTo(orderDto.getAddress());
    }




    @DisplayName("Should throw empty cart Exception")
    @Test
    void shouldThrowEmptyCartException(){

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn(user2.getEmail());
        given(userRepository.getUserByEmail(user2.getEmail())).willReturn(user2);
        given(cartRepository.findCartByCustomer(user2)).willReturn(Optional.of(cart2));

        assertThatThrownBy(() -> checkOutService.makeOrder(checkOutException)).isInstanceOf(CartEmptyException.class);

    }
}