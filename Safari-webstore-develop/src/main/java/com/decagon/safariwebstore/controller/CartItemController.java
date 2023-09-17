package com.decagon.safariwebstore.controller;

import com.decagon.safariwebstore.dto.CartItemDTO;
import com.decagon.safariwebstore.model.User;
import com.decagon.safariwebstore.payload.response.Response;
import com.decagon.safariwebstore.service.CartItemService;
import com.decagon.safariwebstore.service.UserService;
import com.decagon.safariwebstore.utils.JWTUtil;
import com.decagon.safariwebstore.utils.MethodUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
@CrossOrigin
public class CartItemController {

    private final CartItemService cartItemService;
    private final UserService userService;
    private final JWTUtil jwtUtil;

    @PostMapping("/add-to-cart")
    public ResponseEntity<Response> addItemsToCart(HttpServletRequest request,
                                                   @RequestBody CartItemDTO cartItemDTO) {
        String jwt = MethodUtils.parseJwt(request);
        String email = jwtUtil.extractUserName(jwt);
        User user = userService.findUserByEmail(email);
        Response response = cartItemService.addItemToCart(user, cartItemDTO);
        Integer status = response.getStatus();

        if (status.equals(201)) return new ResponseEntity<>(response, HttpStatus.CREATED);
        else if (status.equals(404)) return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @GetMapping("/cart-items")
    @CrossOrigin
    public ResponseEntity<List<CartItemDTO>> getCartItems(HttpServletRequest request) {

        String jwt = MethodUtils.parseJwt(request);
        String email = jwtUtil.extractUserName(jwt);
        User user = userService.findUserByEmail(email);

        return new ResponseEntity<>(cartItemService.getCartItems(user), HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{id}", method=RequestMethod.DELETE)
    @CrossOrigin
    public ResponseEntity<Response> deleteCartItems(HttpServletRequest request, @PathVariable Long id) {
        return cartItemService.deleteCartItemById(id);
    }
}
