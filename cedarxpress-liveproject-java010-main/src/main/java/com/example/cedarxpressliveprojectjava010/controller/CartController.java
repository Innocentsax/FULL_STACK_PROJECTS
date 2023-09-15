package com.example.cedarxpressliveprojectjava010.controller;
import com.example.cedarxpressliveprojectjava010.dto.frontEndDto.CartDto;
import com.example.cedarxpressliveprojectjava010.dto.request.AlterProductQuantityRequest;
import com.example.cedarxpressliveprojectjava010.dto.response.CartItemDto;
import com.example.cedarxpressliveprojectjava010.entity.Cart;
import com.example.cedarxpressliveprojectjava010.entity.CartItem;
import com.example.cedarxpressliveprojectjava010.response.ApiResponse;
import com.example.cedarxpressliveprojectjava010.service.CartItemService;
import com.example.cedarxpressliveprojectjava010.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import javax.websocket.server.PathParam;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/customer/carts")
@PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
@CrossOrigin("*")
public class CartController {
    private CartItemService cartItemService;
    private CartService cartService;

    @PostMapping("/{productId}")
    public ResponseEntity<Cart> addToCart(@PathVariable Long productId) {
        return cartItemService.addToCart(productId);
    }


    @DeleteMapping ("/product/{productId}")
    public ResponseEntity<?> removeFromCart(@PathVariable Long productId) {
        String loggedInEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        cartItemService.removeFromCart(productId,loggedInEmail);
        return new ResponseEntity<>(new ApiResponse<>("product was successfully deleted"), HttpStatus.OK);
    }

    @DeleteMapping ("/")
    public ResponseEntity<?> clearCart() {
        return cartItemService.clearCart();
    }

    @PutMapping("/")
    public ResponseEntity<CartItem> alterProductQuantityInCart(@RequestBody AlterProductQuantityRequest request) {
        return cartItemService.alterProductQuantity(request);
    }
    @GetMapping("/")
    public ResponseEntity<CartDto> getUserCart(){
        return cartService.findCartByUser();
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<String> deleteCartItem(@PathVariable("cartItemId") Long cartId){
        return cartItemService.deleteCartItem(cartId);
    }
}