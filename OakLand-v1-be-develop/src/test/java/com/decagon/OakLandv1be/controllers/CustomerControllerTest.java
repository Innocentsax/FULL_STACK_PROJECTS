package com.decagon.OakLandv1be.controllers;

import com.decagon.OakLandv1be.OakLandV1BeApplication;
import com.decagon.OakLandv1be.dto.ProductCustResponseDto;
import com.decagon.OakLandv1be.dto.CustomerProfileDto;
import com.decagon.OakLandv1be.dto.cartDtos.AddItemToCartDto;
import com.decagon.OakLandv1be.entities.*;
import com.decagon.OakLandv1be.enums.Gender;
import com.decagon.OakLandv1be.enums.Role;
import com.decagon.OakLandv1be.repositries.CartRepository;
import com.decagon.OakLandv1be.services.serviceImpl.CartServiceImpl;
import com.decagon.OakLandv1be.services.serviceImpl.CustomerServiceImpl;
import com.decagon.OakLandv1be.utils.ApiResponse;
import com.decagon.OakLandv1be.utils.ResponseManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(CustomerController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = OakLandV1BeApplication.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CustomerServiceImpl customerService;

    @MockBean
    private CustomerController customerController;
    @MockBean
    private CartServiceImpl cartService;

    @MockBean
    private CartRepository cartRepository;

    @MockBean
    private ResponseManager responseManager;

    List<Product> productList = new ArrayList<>();


//    @Test
//    public void editProfile() throws Exception {
//        EditProfileRequestDto editProfileRequestDto = new EditProfileRequestDto();
//        editProfileRequestDto.setFirstName("Many");
//        editProfileRequestDto.setLastName("Rob");
//
//        editProfileRequestDto.setGender(String.valueOf(Gender.MALE));
//
//        editProfileRequestDto.setGender(String.valueOf(Gender.MALE));
//
//        editProfileRequestDto.setDate_of_birth("11-01-1993");
//        editProfileRequestDto.setPhone("07068693321");
//        doNothing().when(customerService).editProfile(editProfileRequestDto);
//        String requestBody = mapper.writeValueAsString(editProfileRequestDto);
//        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/customer/edit-profile", 42L)
//                        .contentType("application/json").content(requestBody))
//                .andExpect(status().isOk())
//                .andReturn();
//    }



    @Test
    void CustomerController_AddItemToCart_ReturnResponseEntity() {
        try {
            Person person = new Person();
            person.setGender(Gender.FEMALE);
            person.setPhone("1234");
            person.setEmail("a@mail.com");
            person.setDate_of_birth("10-06-1992");
            person.setAddress("123");
            person.setFirstName("Aishat");
            person.setLastName("Moshood");
            person.setVerificationStatus(true);
            person.setRole(Role.CUSTOMER);

            Customer customer = new Customer();
            customer.setPerson(person);

            Product product = Product.builder()
                    .name("Oppola")
                    .price(40000.00)
                    .availableQty(400)
                    .imageUrl("www.google.com")
                    .color("yellow")
                    .description("lovely fur")
                    .build();
            product.setId(1L);

            Item item = new Item();
            item.setId(1L);
            item.setProductName("Oppola");
            item.setImageUrl("www.google.com");
            item.setUnitPrice(40000.00);
            item.setOrderQty(1);
            item.setSubTotal(item.getUnitPrice() * item.getOrderQty());

            Cart cart = person.getCustomer().getCart();

            if (cart == null) {
                cart = new Cart();
                cart.setCustomer(customer);
            }

            Set<Item> cartItemsSet = new HashSet<>();
            cartItemsSet.add(item);
            cart.setItems(cartItemsSet);
            cart.setTotal(40000.0);

            String response = "Item Saved to Cart Successfully";

            ApiResponse apiResponse = new ApiResponse<>("Request Successful", true, response);

            when(customerService.getCurrentlyLoggedInUser()).thenReturn(customer);
            when(cartRepository.findByCustomer(customer)).thenReturn(cart);

            when(cartService.addItemToCart(1L)).thenReturn(response);

            when(responseManager.success(response)).thenReturn(apiResponse);

            mockMvc.perform(post("/api/v1/auth/customer/cart/item/add/productId", 1L).contentType("application/json"))
                    .andExpect(status().isCreated());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void customerToRemoveProductsFromFavorites() throws Exception {
//        Long pid = 1L;
//
//        String requestBody = mapper.writeValueAsString(pid);
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/customer/product/favorites/remove/{pid}", 1L)
//                        .contentType("application/json")
//                        .content(requestBody))
//                .andExpect(status().isAccepted());
    }

    @Test
    void viewProfile() {
        try {
            CustomerProfileDto customerProfileDto = CustomerProfileDto.builder().firstName("John").lastName("Doe").email("email.com")
                    .phone("1234567890").address("123 Main St").date_of_birth("01-01-2000").gender(Gender.MALE).verificationStatus(true).build();
            when(customerService.viewProfile()).thenReturn(customerProfileDto);
            mockMvc.perform(get("/api/v1/customer/view-profile", 12L)
                            .contentType("application/json"))
                    .andExpect(status().isOk());
            verify(customerService, times(1)).viewProfile();
        } catch (Exception xe) {
            xe.printStackTrace();
        }
    }
    @Test
    void viewAllProfilesPaginationAndSort() {
       try {
           int pageNumber = 0;
           int pageSize = 16;
           String sortBy = "id";
           List<Customer> customers = Arrays.asList(new Customer(), new Customer());
           List<CustomerProfileDto> customerProfileDtoList = Arrays.asList(new CustomerProfileDto(), new CustomerProfileDto());
           Page<CustomerProfileDto> customerProfileDtoPage = new PageImpl<>(customerProfileDtoList);
           when(customerService.viewAllCustomersProfileWithPaginationSorting(pageNumber, pageSize, sortBy)).thenReturn(customerProfileDtoPage);
           String requestBody = mapper.writeValueAsString(pageNumber);
           String requestBodie = mapper.writeValueAsString(pageSize);
           String requestBod = mapper.writeValueAsString(sortBy);
           ApiResponse<Page<CustomerProfileDto>> actualResponse = customerController.viewAllProfilesPaginationAndSort(0, 16, "id");
//       mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customer/admin/customers-profile/page-sort")
//               .contentType("application/json")
//               .content(requestBody)
//               .content(requestBodie)
//               .content(requestBod))
//               .andReturn();
           Assertions.assertEquals("Paginated", actualResponse.getMessage());
           Assertions.assertEquals(customerProfileDtoPage, actualResponse.getData());
           Assertions.assertEquals(true, actualResponse.getStatus());
           verify(customerService).viewAllCustomersProfileWithPaginationSorting(0, 16, "id");
       } catch (Exception ce){
           ce.printStackTrace();
       }
    }

    @Test
    void viewASingleFavorite() {
        try {
            Product product = new Product();
            product.setId(2L);
            product.setName("center table");
            product.setImageUrl("image");
            product.setColor("red");
            product.setPrice(1000.00);
            product.setDescription("round shape center table");
            ProductCustResponseDto productCustResponseDto = ProductCustResponseDto.builder()
                    .name("foreign chair")
                    .price(200.00)
                    .color("red")
                    .imageUrl("cccc")
                    .description("center chair")
                    .build();
            when(customerService.viewASingleFavorite(anyLong()))
                    .thenReturn(productCustResponseDto);
            mockMvc.perform(MockMvcRequestBuilders.get("/Api/v1/customer/view/{product_id}", 2L).
                    contentType("Application/Json"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }}




