package com.decagon.chompapp.controllers;

import com.decagon.chompapp.dtos.*;
import com.decagon.chompapp.enums.OrderStatus;
import com.decagon.chompapp.enums.PaymentMethod;
import com.decagon.chompapp.models.*;
import com.decagon.chompapp.services.OrderServices;
import com.decagon.chompapp.services.ProductServices;
import com.decagon.chompapp.services.paystack.PaymentServiceImpl;
import com.decagon.chompapp.utils.AppConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private ProductServices productServices;

    @MockBean
    private PaymentServiceImpl paymentService;

    @MockBean
    private OrderServices orderServices;

    ResponseEntity<ProductResponse> responseEntity;

    User user;

    User user1;

    UserDetails userDetails;

    Page<Order> orders;

    Order newOrder;

    List<OrderItem> orderItems= new ArrayList<>();

    OrderResponseDto orderResponseDto;

    ResponseEntity<OrderResponse> responseEntity2;

    @BeforeEach
    void setUp() throws ServletException {
        user = User.builder().email("stanley@gmail.com").build();

        Category burger = Category.builder().categoryId(1L).categoryName("Burger").build();
        Product product = Product.builder().productId(1L).productName("Product 1").size("small").category(burger).build();
        List<Product> listOfProducts = new ArrayList<>();
        listOfProducts.add(product);
        Page<Product> products = new PageImpl<>(listOfProducts);
        ProductDto productDto = ProductDto.builder().productId(1L).productName("Product 1").size("small").categoryName(product.getCategory().getCategoryName()).build();
        List<ProductDto> content = new ArrayList<>();
        content.add(productDto);
        ProductResponse productResponse = ProductResponse.builder()
                .content(content)
                .pageNo(products.getNumber())
                .totalPages(products.getTotalPages())
                .pageSize(products.getSize())
                .totalElements(products.getTotalElements())
                .last(products.isLast())
                .build();

        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        responseEntity = new ResponseEntity<>(productResponse, httpHeaders, HttpStatus.OK);
        when(productServices.getAllProducts(anyInt(), anyInt(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(responseEntity);
        when(productServices.fetchSingleProductById(anyLong())).thenReturn(new ResponseEntity<>(productDto, httpHeaders, HttpStatus.OK));
        when(productServices.getAllProducts(anyInt(), anyInt(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(responseEntity);
    }

    @Test
    void testsThatTheControllerListensForCorrectHttpRequestWhichIsGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/auth/users/getAllProducts").param("pageSize", "10").accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    }

    @Test
    void testsThatTheControllerListensForCorrectHttpRequestWhichIsGetAndThrows500IfNotGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/users/getAllProducts").accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isInternalServerError()).andReturn();
    }

    @Test
    void testsThatBusinessLogicIsCalledWhenTheUrlIsHit() throws Exception {
        mockMvc.perform((MockMvcRequestBuilders.get("/api/v1/auth/users/getAllProducts").param("pageNo", AppConstants.DEFAULT_PAGE_NUMBER).param("pageSize", AppConstants.DEFAULT_PAGE_SIZE).param("sortBy", AppConstants.DEFAULT_SORT_BY)).accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Integer> integerArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(productServices, times(1)).getAllProducts(integerArgumentCaptor.capture(), integerArgumentCaptor.capture(), stringArgumentCaptor.capture(), stringArgumentCaptor.capture(), stringArgumentCaptor.capture(), stringArgumentCaptor.capture(), stringArgumentCaptor.capture(), stringArgumentCaptor.capture());
        Assertions.assertEquals(integerArgumentCaptor.getValue(), 10);
    }

    @Test
    void testsThatTheCorrectResponseIsReturnedWhichInThisCaseIsAResponseEntityOfProductResponse() throws Exception {
        MvcResult mvcResult = mockMvc.perform((MockMvcRequestBuilders.get("/api/v1/auth/users/getAllProducts").param("pageNo", AppConstants.DEFAULT_PAGE_NUMBER).param("pageSize", AppConstants.DEFAULT_PAGE_SIZE).param("sortBy", AppConstants.DEFAULT_SORT_BY)).param("sortDir", AppConstants.DEFAULT_SORT_DIRECTION).param("filterBy", "").param("filterParam", AppConstants.DEFAULT_FILTER_PARAMETER)).andExpect(jsonPath("$.pageNo").value(0)).andExpect(jsonPath("$.pageSize").value(1)).andExpect(jsonPath("$.totalElements").value(1)).andExpect(jsonPath("$.totalPages").value(1)).andExpect(jsonPath("$.last").value(true)).andReturn();
        String expectedResponse = objectMapper.writeValueAsString(responseEntity.getBody());
        String actualResponse = mvcResult.getResponse().getContentAsString();
        Assertions.assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void testsThatTheControllerListensForCorrectHttpRequestWhichIsGetForSingleProduct() throws Exception {
        ProductDto productDto = ProductDto.builder().productId(1L).productName("Cheesy Burger").productPrice(1000.00)
                .categoryName("sides").build();
        when(productServices.fetchSingleProductById(1L)).thenReturn(new ResponseEntity<>(productDto, HttpStatus.OK));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/auth/users/fetch-single-product/1").accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testsThatTheControllerListensForCorrectHttpRequestWhichIsGetAndThrows500IfNotGetSingleProduct() throws Exception {
        when(productServices.fetchSingleProductById(1L)).thenThrow(new RuntimeException());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/auth/users/fetch-single-product/1").accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    @Test
    void viewOrderHistoryForAPremiumUser () throws Exception {
        user1 = User.builder()
                .userId(1L)
                .email("james@mail.com")
                .password("password")
                .username("james@mail.com")
                .firstName("James")
                .build();
        userDetails = new org.springframework.security.core.userdetails.User(user1.getEmail(), user1.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_PREMIUM")));
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),userDetails.getPassword(),userDetails.getAuthorities());
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        newOrder = Order.builder()
                .orderId(1L)
                .flatRate(0.00)
                .totalPrice(1000.00)
                .orderItems(orderItems)
                .status(OrderStatus.PENDING)
                .paymentMethod(PaymentMethod.PAY_WITH_CARD)
                .shippingAddress(new ShippingAddress())
                .orderItems(orderItems)
                .user(user1)
                .build();
        List<Order> listOfOrders = new ArrayList<>();
        listOfOrders.add(newOrder);
        orders = new PageImpl<>(listOfOrders);
        orderResponseDto = OrderResponseDto.builder()
                .orderId(newOrder.getOrderId())
                .flatRate(newOrder.getFlatRate())
                .total(newOrder.getTotalPrice())
                .status(newOrder.getStatus())
                .paymentMethod(newOrder.getPaymentMethod())
                .shippingAddress(newOrder.getShippingAddress())
                .build();
        List<OrderResponseDto> content = new ArrayList<>();
        content.add(orderResponseDto);
        OrderResponse orderResponse = OrderResponse.builder()
                .content(content)
                .pageNo(orders.getNumber())
                .totalPages(orders.getTotalPages())
                .pageSize(orders.getSize())
                .totalElements(orders.getTotalElements())
                .last(orders.isLast())
                .build();
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        responseEntity2 = new ResponseEntity<>(orderResponse, httpHeaders, HttpStatus.OK);
        Mockito.when(orderServices.viewOrderHistoryForAPremiumUser(anyInt(),anyInt(),anyString(),anyString())).thenReturn(responseEntity2);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/users/orders").accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isInternalServerError()).andReturn();

        mockMvc.perform((MockMvcRequestBuilders.get("/api/v1/auth/users/orders").param("pageNo", AppConstants.DEFAULT_PAGE_NUMBER).param("pageSize", AppConstants.DEFAULT_PAGE_SIZE).param("sortBy", AppConstants.DEFAULT_SORT_BY_ORDER)).accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(jsonPath("$.pageNo").value(0)).andExpect(jsonPath("$.pageSize").value(1)).andExpect(jsonPath("$.totalElements").value(1)).andExpect(jsonPath("$.totalPages").value(1)).andExpect(jsonPath("$.last").value(true)).andReturn();

        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Integer> integerArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(orderServices,times(1)).viewOrderHistoryForAPremiumUser(integerArgumentCaptor.capture(),integerArgumentCaptor.capture(),stringArgumentCaptor.capture(),stringArgumentCaptor.capture());
        Assertions.assertEquals(integerArgumentCaptor.getValue(),10);
    }
}