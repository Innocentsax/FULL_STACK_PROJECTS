package com.decagon.OakLandv1be.services.serviceImpl;

import com.decagon.OakLandv1be.dto.cartDtos.AddItemToCartDto;

import com.decagon.OakLandv1be.entities.*;
import com.decagon.OakLandv1be.enums.Gender;
import com.decagon.OakLandv1be.enums.Role;
import com.decagon.OakLandv1be.exceptions.AlreadyExistsException;
import com.decagon.OakLandv1be.services.CartService;
import com.decagon.OakLandv1be.repositries.*;
import com.decagon.OakLandv1be.services.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.*;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@RunWith(MockitoJUnitRunner.class)
class CartServiceImplTest {

    @InjectMocks
    CartServiceImpl cartServiceImpl;

    @Mock
    ProductRepository productRepository;

    @Mock
    CartService cartService;

    @Mock
    CartRepository cartRepository;

    @Mock
    ItemRepository itemRepository;
    @Mock
    PersonRepository personRepository;
    @MockBean
    CustomerService customerService;


//    @Test
//    void testAddItemToCart() throws AlreadyExistsException {
//        Person person = new Person();
//        person.setGender(Gender.FEMALE);
//        person.setPhone("1234");
//        person.setEmail("a@mail.com");
//        person.setDate_of_birth("10-06-1992");
//        person.setAddress("123");
//        person.setFirstName("Aishat");
//        person.setLastName("Moshood");
//        person.setVerificationStatus(true);
//        person.setRole(Role.CUSTOMER);
//
//        Customer customer = new Customer();
//        customer.setPerson(person);
//
//        Product product = new Product();
//        product.setId(1L);
//        product.setName("ArmChair");
//        product.setImageUrl("abcd");
//        product.setAvailableQty(50);
//        product.setPrice(2000.0);
//
//        Item item = new Item();
//        item.setId(1L);
//        item.setProductName("ArmChair");
//        item.setImageUrl("abcd");
//        item.setUnitPrice(2000.0);
//        item.setOrderQty(20);
//        item.setSubTotal(item.getUnitPrice() * item.getOrderQty());
//        item.setProduct(product);
//
//        Set<Item> cartItemsSet = new HashSet<>();
//        cartItemsSet.add(item);
//
//        Cart cart = new Cart();
//        cart.setCustomer(customer);
//        cart.setItems(cartItemsSet);
//        cart.setTotal(40000.0);
//
//        AddItemToCartDto addItemToCartDto = new AddItemToCartDto();
//        addItemToCartDto.setOrderQty(20);
//        String response = "Item Saved to Cart Successfully";
//
//     //   when(customerService.getCurrentlyLoggedInUser()).thenReturn(customer);
//        when(cartRepository.findByCustomer(customer)).thenReturn(cart);
//        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
//
//        assertEquals(response,cartServiceImpl.addItemToCart(1L,addItemToCartDto));
//    }


  @Test
    public void removeItemsInCart() {
        Item item = new Item();
        Product product = Product.builder().name("Tall dinning chair").price(2000.00).imageUrl("hgdhg")
                .color("green").description("strong black").build();
        item.setId(2L);

        item.setProductName(product.getName());
        item.setOrderQty(3);
        item.setUnitPrice(100.00);
        item.setSubTotal(300.00);
       when(itemRepository.save(item)).thenReturn(item);
       when(itemRepository.findById(2L)).thenReturn(Optional.of(item));
        cartService.removeItem(item.getId());
        verify(itemRepository).deleteById(item.getId());

    }

}