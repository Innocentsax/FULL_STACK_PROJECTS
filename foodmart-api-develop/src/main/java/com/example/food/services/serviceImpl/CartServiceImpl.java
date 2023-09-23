package com.example.food.services.serviceImpl;

import com.example.food.Enum.ResponseCodeEnum;
import com.example.food.dto.CartItemDto;
import com.example.food.model.Cart;
import com.example.food.model.CartItem;
import com.example.food.model.Product;
import com.example.food.model.Users;
import com.example.food.pojos.CartResponse;
import com.example.food.repositories.CartItemRepository;
import com.example.food.repositories.CartRepository;
import com.example.food.repositories.ProductRepository;
import com.example.food.repositories.UserRepository;
import com.example.food.restartifacts.BaseResponse;
import com.example.food.services.CartService;
import com.example.food.util.ResponseCodeUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional
@AllArgsConstructor
@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final ResponseCodeUtil responseCodeUtil = new ResponseCodeUtil();

    private Users getLoggedInUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(authentication)
                .orElseThrow(() -> new RuntimeException("User not authorized"));
    }

    @Override
    public BaseResponse removeCartItem(long cartItemId) {
        CartResponse baseResponse = new CartResponse();
        try {
            Users user = getLoggedInUser();
            Cart cart = cartRepository.findByUsersEmail(user.getEmail()).orElseThrow(RuntimeException::new);
            Optional<CartItem> cartItemCheck = cartItemRepository.findById(cartItemId);
            if (cartItemCheck.isPresent()) {
                CartItem cartItem = cartItemCheck.get();
                removeItem(cartItemId, cart, cartItem);

                CartResponse cartResponse = mapCartItemToDto(cart);
                return responseCodeUtil.updateResponseData(cartResponse, ResponseCodeEnum.SUCCESS, "Item removed from user cart");

            } else {
                responseCodeUtil.updateResponseData(baseResponse, ResponseCodeEnum.ERROR, "Item is not in user cart");
            }
            return baseResponse;
        } catch (Exception e) {
            log.error("Email not registered, Product cannot be removed: {}", e.getMessage());
        }
        return responseCodeUtil.updateResponseData(baseResponse, ResponseCodeEnum.ERROR);
    }

    @Override
    public CartResponse addCartItem(Long productId) {
        try {
            Users user = getLoggedInUser();
            Product product = productRepository.findById(productId).orElseThrow(RuntimeException::new);
            Cart userCart = cartRepository.findByUsersEmail(user.getEmail()).orElseThrow(RuntimeException::new);
            Optional<CartItem> DbCartItem = cartItemRepository.findCartItemByCartIdAndProductId(userCart.getId(), productId);
            CartItem cartItem;
            if (DbCartItem.isEmpty() && userCart.getCartItemList().isEmpty()) {
                if (product.getQuantity() < 1)
                    return responseCodeUtil.updateResponseData(new CartResponse(), ResponseCodeEnum.ERROR, product.getProductName() + " is out of stock");
                cartItem = new CartItem();
                cartItem.setCart(userCart);
                cartItem.setProduct(product);
                cartItem.setQuantity(1);
                cartItem.setSubTotal(product.getProductPrice());
                CartItem savedCartItem = cartItemRepository.save(cartItem);

                userCart.setCartTotal(cartItem.getSubTotal());
                userCart.getCartItemList().add(savedCartItem);
                userCart.setQuantity(userCart.getCartItemList().size());
                cartRepository.save(userCart);

            } else if (DbCartItem.isEmpty()) {
                if (product.getQuantity() < 1)
                    return responseCodeUtil.updateResponseData(new CartResponse(), ResponseCodeEnum.ERROR, product.getProductName() + " is out of stock");
                cartItem = new CartItem();
                cartItem.setCart(userCart);
                cartItem.setProduct(product);
                cartItem.setQuantity(1);
                cartItem.setSubTotal(product.getProductPrice());
                CartItem savedCartItem = cartItemRepository.save(cartItem);

                userCart.setCartTotal(userCart.getCartTotal().add(cartItem.getSubTotal()));
                userCart.getCartItemList().add(savedCartItem);
                userCart.setQuantity(userCart.getCartItemList().size());
                cartRepository.save(userCart);
            } else {
                cartItem = DbCartItem.get();
                if (cartItem.getQuantity() == product.getQuantity()) {
                    CartResponse cartResponse = mapCartItemToDto(userCart);
                    return responseCodeUtil.updateResponseData(cartResponse, ResponseCodeEnum.ERROR, "we have only " + product.getQuantity() + " " + product.getProductName() + " in stock");
                }
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                cartItem.setSubTotal(cartItem.getSubTotal().add(product.getProductPrice()));
                cartItem.setCart(userCart);
                cartItemRepository.save(cartItem);

                userCart.setCartTotal(userCart.getCartTotal().add(product.getProductPrice()));
                userCart.setQuantity(userCart.getCartItemList().size());
                userCart = cartRepository.save(userCart);
            }
            CartResponse cartResponse = mapCartItemToDto(userCart);
            return responseCodeUtil.updateResponseData(cartResponse, ResponseCodeEnum.SUCCESS, product.getProductName() + " added");
        } catch (Exception e) {
            log.error("An error occurred, Product cannot be added: {}", e.getMessage());
            return responseCodeUtil.updateResponseData(new CartResponse(), ResponseCodeEnum.ERROR);
        }
    }

    @Override
    @Transactional
    public CartResponse clearCart() {
        try {
            Users user = getLoggedInUser();
            Cart userCart = cartRepository.findByUsersEmail(user.getEmail()).orElseThrow(RuntimeException::new);

            for (CartItem item : userCart.getCartItemList()) {
                cartItemRepository.deleteById(item.getId());
            }
            userCart.setCartTotal(BigDecimal.valueOf(0));
            userCart.setQuantity(0);
            cartRepository.save(userCart);

            CartResponse response = CartResponse.builder()
                    .cartTotal(BigDecimal.valueOf(0))
                    .cartItemList(new ArrayList<>())
                    .quantity(0)
                    .build();
            response.setCode(0);
            return responseCodeUtil.updateResponseData(response, ResponseCodeEnum.SUCCESS, "Cart Cleared");
        } catch (Exception e) {
            log.error("An Error Occurred: {}", e.getMessage());
            return responseCodeUtil.updateResponseData(new CartResponse(), ResponseCodeEnum.ERROR);
        }
    }

    @Override
    public CartResponse reduceProductQuantity(Long productId) {
        try {
            Users user = getLoggedInUser();
            Cart userCart = cartRepository.findByUsersEmail(user.getEmail()).orElseThrow(RuntimeException::new);
            Product product = productRepository.findById(productId).orElseThrow(RuntimeException::new);
            CartItem cartItem = cartItemRepository.findCartItemByCartIdAndProductId(userCart.getId(), productId).orElseThrow(RuntimeException::new);

            CartResponse cartResponse;
            if (cartItem.getQuantity() == 1) {
                removeItem(cartItem.getId(), userCart, cartItem);
            } else {
                cartItem.setQuantity(cartItem.getQuantity() - 1);
                cartItem.setSubTotal(cartItem.getSubTotal().subtract(product.getProductPrice()));
                cartItemRepository.save(cartItem);
                userCart.setCartTotal(userCart.getCartTotal().subtract(product.getProductPrice()));
            }
            cartResponse = mapCartItemToDto(userCart);
            String description = userCart.getQuantity() > 0 ? product.getProductName() + " reduced" : "Cart is now empty";
            return responseCodeUtil.updateResponseData(cartResponse, ResponseCodeEnum.SUCCESS, description);
        } catch (Exception e) {
            log.error("An error occurred, Product cannot be added: {}", e.getMessage());
            return responseCodeUtil.updateResponseData(new CartResponse(), ResponseCodeEnum.ERROR);
        }
    }

    @Override
    public CartResponse viewCartItems() {
        try {
            Users user = getLoggedInUser();
            Cart userCart = cartRepository.findByUsersEmail(user.getEmail()).orElseThrow(RuntimeException::new);
            return responseCodeUtil.updateResponseData(mapCartItemToDto(userCart), ResponseCodeEnum.SUCCESS);
        } catch (Exception e) {
            log.error("An error occurred: {}", e.getMessage());
            return responseCodeUtil.updateResponseData(new CartResponse(), ResponseCodeEnum.ERROR);
        }
    }

    private void removeItem(long cartItemId, Cart cart, CartItem cartItem) {
        cartItemRepository.deleteById(cartItemId);
        int index = cart.getCartItemList().indexOf(cartItem);
        cart.setCartTotal(cart.getCartTotal().subtract(cartItem.getSubTotal()));
        cart.setQuantity(cart.getQuantity() - 1);
        cart.getCartItemList().remove(index);
        cartRepository.save(cart);
    }

    private CartResponse mapCartItemToDto(Cart userCart) {
        List<CartItemDto> cartItemDtoList = new ArrayList<>();
        for (CartItem item : userCart.getCartItemList()) {
            ObjectMapper objectMapper = new ObjectMapper();
            CartItemDto cartItemDto = objectMapper.convertValue(item, CartItemDto.class);
            cartItemDtoList.add(cartItemDto);
        }
        return CartResponse.builder()
                .cartItemList(cartItemDtoList)
                .cartTotal(userCart.getCartTotal())
                .quantity(userCart.getQuantity())
                .build();
    }
}
