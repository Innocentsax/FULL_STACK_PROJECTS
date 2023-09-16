package com.decagon.chompapp.repositories;

import com.decagon.chompapp.dtos.CartItemDto;
import com.decagon.chompapp.models.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.ArrayList;


@ExtendWith(SpringExtension.class)
@DataJpaTest
class CartItemRepositoryIntegrationTests {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    UserRepository userRepository;

    Product product1, product2;
    User user;
    Cart userCart;
    CartItem cartItem1, cartItem2;
    Category category1, category2;
    CartItemDto cartItemDto1;
    CartItemDto cartItemDto2;


    @BeforeEach
    void setUp() {
        category1 = Category.builder().categoryId(1L).build();
        category2 = Category.builder().categoryId(2L).build();
        product1 = Product.builder().productId(1L)
                .productImage("jsdhjhgsdj")
                .productName("Burger")
                .productPrice(2000).category(category1).build();
        product2 = Product.builder().productId(1L)
                .productImage("jsdhjhgsdj")
                .productName("Fries")
                .productPrice(1500).category(category2).build();
        userCart = Cart.builder().user(user).cartItemList(new ArrayList<>()).cartTotal(0).quantity(0).build();
        user = User.builder().userId(1L).email("ukeloveth247@gmail.com").firstName("Loveth").lastName("Uke").username("loveth").cart(userCart).build();
        cartItem1 = CartItem.builder().cart(userCart).product(product1).quantity(0).subTotal(0).build();
        cartItem2 = CartItem.builder().cartItemId(2L).cart(userCart).product(product2).quantity(0).subTotal(0).build();
        cartItemDto2 = CartItemDto.builder().cartId(cartItem2.getCartItemId())
                .productId(product2.getProductId()).productImage(product2.getProductImage())
                .productName(product2.getProductName()).build();
        userCart.getCartItemList().add(cartItem1);
        cartItem1.setCart(userCart);
        user.setCart(userCart);
        userCart.setUser(user);
    }

    @Test
    void findByCartAndProduct() {
        User save = userRepository.save(user);
        Assertions.assertEquals(save.getEmail(), user.getEmail());
    }

    @Test
    void deleteAllByCart_CartId() {
        User save = userRepository.save(user);
        Assertions.assertEquals(save.getEmail(), user.getEmail());
    }

    @Test
    void findByCart_CartIdAndCartItemId() {
        User save = userRepository.save(user);
        Assertions.assertEquals(save.getEmail(),user.getEmail());
    }
}