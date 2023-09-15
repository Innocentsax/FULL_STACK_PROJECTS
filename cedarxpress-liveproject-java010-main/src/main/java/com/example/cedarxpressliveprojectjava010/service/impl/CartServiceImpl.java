package com.example.cedarxpressliveprojectjava010.service.impl;
import com.example.cedarxpressliveprojectjava010.dto.frontEndDto.CartDto;
import com.example.cedarxpressliveprojectjava010.dto.frontEndDto.CartItemDto;
import com.example.cedarxpressliveprojectjava010.entity.Cart;
import com.example.cedarxpressliveprojectjava010.entity.CartItem;
import com.example.cedarxpressliveprojectjava010.entity.User;
import com.example.cedarxpressliveprojectjava010.exception.CartNotFoundException;
import com.example.cedarxpressliveprojectjava010.exception.UserNotFoundException;
import com.example.cedarxpressliveprojectjava010.repository.CartRepository;
import com.example.cedarxpressliveprojectjava010.repository.UserRepository;
import com.example.cedarxpressliveprojectjava010.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {
    private UserRepository userRepository;
    private CartRepository cartRepository;
    @Override
    public ResponseEntity<CartDto> findCartByUser() {
        String loggedInEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = Optional.ofNullable(userRepository.getUserByEmail(loggedInEmail)).orElseThrow(() -> new UserNotFoundException("User not found"));

//        User user = Optional.ofNullable(userRepository.getUserByEmail("general@gmail.com")).orElseThrow(() -> new UserNotFoundException("User not found"));
        Cart cart = cartRepository.findCartByCustomer(user).orElseThrow(()-> new CartNotFoundException("Cart Not Found"));
        CartDto cartDto = CartDto.builder()
                .id(cart.getId())
                .cartItemDto(mapToCartItems(cart.getCartItems()))
                .build();
        return ResponseEntity.ok(cartDto);
    }
    public List<CartItemDto> mapToCartItems(List<CartItem> cartItems){
        List<CartItemDto> list = new ArrayList<>();
        for(CartItem cartItem: cartItems){
            BigDecimal price = BigDecimal.valueOf(cartItem.getProduct().getPrice());
            String img = null;
            String product = cartItem.getProduct().getProductName();
            Long id =cartItem.getId();
            int quantity = cartItem.getUnit();
            if(cartItem.getProduct().getImages() == null){
                img = null;
            }else if(cartItem.getProduct().getImages().size() == 0){
                img = null;
            }else{
                img = cartItem.getProduct().getImages().get(0).getImg();
            }
            CartItemDto cid = CartItemDto.builder()
                    .id(id)
                    .img(img)
                    .product(product)
                    .price(price)
                    .quantity(quantity)
                    .total(quantity * price.doubleValue())
                    .build();
            list.add(cid);
        }
        return list;
    }
}