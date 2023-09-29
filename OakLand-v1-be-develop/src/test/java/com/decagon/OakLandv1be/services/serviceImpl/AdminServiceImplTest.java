package com.decagon.OakLandv1be.services.serviceImpl;

import com.decagon.OakLandv1be.dto.AddressResponseDto;
import com.decagon.OakLandv1be.dto.OperationStatus;
import com.decagon.OakLandv1be.dto.ProductResponseDto;
import com.decagon.OakLandv1be.entities.*;
import com.decagon.OakLandv1be.repositries.CustomerRepository;
import com.decagon.OakLandv1be.repositries.PersonRepository;
import com.decagon.OakLandv1be.repositries.ProductRepository;
import com.decagon.OakLandv1be.utils.UserUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.decagon.OakLandv1be.utils.UserUtil.extractEmailFromPrincipal;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class AdminServiceImplTest {
    @Mock
    ProductRepository productRepository;

    @Mock
    PersonRepository personRepository;
    @Mock
    Authentication authentication;
    @Mock
    SecurityContext securityContext;

    @Mock
    CustomerRepository customerRepository;
    @InjectMocks
    AdminServiceImpl adminService;

    private Product product;
    private ProductResponseDto productResponseDto;
    private SubCategory subCategory;

    private OperationStatus operationStatus;

    Person person;
    Customer customer;
    Set<Address> addressList;
    Address address;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        subCategory = new SubCategory();
        SecurityContextHolder.setContext(securityContext);
        product = Product.builder()
                .name("Tall dinning chair")
                .price(2000.00)
                .imageUrl("hgdhg")
                .availableQty(3)
                .subCategory(subCategory)
                .color("green")
                .description("strong black").build();
        productResponseDto = ProductResponseDto.builder()
                .name("Tall dinning chair")
                .price("#2000.00")
                .imageUrl("hgdhg")
                .availableQty(3)
                .subCategory(subCategory)
                .color("green")
                .description("strong black").build();

        person = Person.builder()
                .email("email@gmail.com")
                .build();

        customer = new Customer();
        addressList = new HashSet<>();
        address = new Address();
        address.setId(1L);
        address.setFullName("Mont Rage");
        address.setStreet("24 Ronny St");
        address.setState("Lagos");
        address.setCountry("Nigeria");
        address.setPhone("07068604532");
        address.setIsDefault(true);
        addressList.add(address);
        customer.setAddressBook(addressList);
    }

    @Test
    void fetchASingleProductShouldReturnProduct() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        Assertions.assertEquals(productResponseDto, adminService.fetchASingleProduct(anyLong()));
    }

    @Test
    void testDeleteProductById() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        adminService.deleteProduct(1L);
        verify(productRepository, times(1)).delete(product);
    }

    @Test
    void viewCustomerAddresses() {
        when(authentication.getName()).thenReturn(person.getEmail());
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(extractEmailFromPrincipal().get()).thenReturn(person.getEmail());
        when(personRepository.findByEmail(person.getEmail())).thenReturn(Optional.of(person));
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        Set<AddressResponseDto> result = adminService.viewAllCustomerAddress(1L);

        AddressResponseDto response = result.iterator().next();

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("Mont Rage", response.getFullName());
        Assertions.assertEquals("24 Ronny St", response.getStreet());
        Assertions.assertEquals("Lagos", response.getState());
        Assertions.assertEquals("Nigeria", response.getCountry());
        Assertions.assertEquals("07068604532", response.getPhone());
        assertTrue(response.getIsDefault());
    }
}