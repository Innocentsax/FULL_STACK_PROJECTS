package com.decagon.OakLandv1be.services.serviceImpl;

import com.decagon.OakLandv1be.dto.CartDto;
import com.decagon.OakLandv1be.dto.cartDtos.CartItemResponseDto;
import com.decagon.OakLandv1be.entities.*;
import com.decagon.OakLandv1be.exceptions.NotAvailableException;
import com.decagon.OakLandv1be.exceptions.*;
import com.decagon.OakLandv1be.repositries.*;
import com.decagon.OakLandv1be.services.CartService;
import lombok.RequiredArgsConstructor;
import com.decagon.OakLandv1be.entities.Cart;
import com.decagon.OakLandv1be.entities.Item;
import com.decagon.OakLandv1be.entities.Person;
import com.decagon.OakLandv1be.repositries.CartRepository;
import com.decagon.OakLandv1be.repositries.ItemRepository;
import com.decagon.OakLandv1be.repositries.PersonRepository;
import com.decagon.OakLandv1be.services.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;


@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final ProductRepository productRepository;
    private final CustomerService customerService;
    private final CustomerRepository customerRepository;
    private final PersonRepository personRepository;
    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;

    @Override
    public String addItemToCart(Long productId) {
        Customer loggedInCustomer = customerService.getCurrentlyLoggedInUser();
        Cart cart = loggedInCustomer.getCart();
        Product product = productRepository.findById(productId).orElseThrow(() -> new NotAvailableException("Product not available"));
        Set<Item> allCartItems = cart.getItems();

        if (product.getAvailableQty() == 0) {
            throw new NotAvailableException("Product out of stock");
        } else if(itemRepository.findByProductId(productId) != null){
            addToItemQuantity(productId);
            return "Item added to cart";
        }

        Item newCartItem = Item.builder()
                .imageUrl(product.getImageUrl())
                .productName(product.getName())
                .unitPrice(product.getPrice())
                .orderQty(1)
                .product(product)
                .cart(cart)
                .build();
        newCartItem.setSubTotal(newCartItem.getOrderQty() * product.getPrice());

        itemRepository.save(newCartItem);
        allCartItems.add(newCartItem);

        cart.setItems(allCartItems);
        Double cartTotal = 0.0;

        for (Item item : cart.getItems()) {
            cartTotal += item.getSubTotal();
        }

        cart.setTotal(cartTotal);
        cartRepository.save(cart);
        customerRepository.save(loggedInCustomer);
        return "Item added to cart!";
    }


    @Override
    public String removeItem(Long itemToRemoveId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String email = authentication.getName();
            Person person = personRepository.findByEmail(email)
                    .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

            Cart cart = person.getCustomer().getCart();
            if (cart == null) throw new ResourceNotFoundException("cart is empty");

            Item item = itemRepository.findById(itemToRemoveId)
                    .orElseThrow(() -> new ResourceNotFoundException("Item does not exist"));
            Set<Item> itemsInCart = cart.getItems();
            // for (Item item : itemsInCart) {
            //if (item.getId() == itemToRemoveId) {

            if (itemsInCart.contains(item)) {
                cart.setTotal(cart.getTotal() - (item.getOrderQty() * item.getUnitPrice()));
                cartRepository.save(cart);
                itemRepository.delete(item);
            }

            return "item removed successfully";

        }
        throw new UnauthorizedUserException("User does not exist");
    }

    @Override
    public String addToItemQuantity(Long productId) {
        Customer loggedInCustomer = customerService.getCurrentlyLoggedInUser();
        Cart cart = loggedInCustomer.getCart();
        Set<Item> cartItems = cart.getItems();
        Double cartTotal = cart.getTotal();

        Item foundItem = itemRepository.findByProductId(productId);

        if (foundItem.getProduct().getAvailableQty() == 0)
            throw new NotAvailableException(foundItem.getProduct().getName() + " out of stock");


        for (Item item : cartItems) {
            if(item.getProduct().getId() == foundItem.getProduct().getId()) {
                foundItem.setOrderQty(item.getOrderQty() + 1);
                foundItem.setSubTotal(item.getUnitPrice() * item.getOrderQty());
                itemRepository.save(foundItem);
            }
        }

        cartTotal += foundItem.getUnitPrice();
        cart.setTotal(cartTotal);
        cartRepository.save(cart);
        return "Item quantity updated successfully";
    }

    @Override
    public String reduceItemQuantity(Long productId) {
        String response = "";
        Item foundItem = itemRepository.findByProductId(productId);
        Customer loggedInCustomer = customerService.getCurrentlyLoggedInUser();
        Cart cart = loggedInCustomer.getCart();
        Set<Item> cartItems = cart.getItems();

        for(Item item : cartItems) {
            if(item.getProduct().getId() == foundItem.getProduct().getId()){
                foundItem.setOrderQty(item.getOrderQty() - 1);
                foundItem.setSubTotal(item.getUnitPrice() * item.getOrderQty());
                itemRepository.save(item);
                response += item.getProductName() + " quantity updated successfully";
            }
        };

        cart.setTotal(cart.getTotal() - foundItem.getUnitPrice());
        cartRepository.save(cart);
        customerRepository.save(loggedInCustomer);
        return response;
    }

    @Override
    public String clearCart(){
        Customer loggedInCustomer = customerService.getCurrentlyLoggedInUser();
        Cart cart = loggedInCustomer.getCart();
        Set<Item> cartItems = cart.getItems();

        for(Item item : cartItems){
            itemRepository.deleteById(item.getId());
        }

        cartItems.clear();
        cart.setItems(cartItems);
        cart.setTotal(0.0);
        cartRepository.save(cart);
        return "Cart cleared successfully";
    }

    @Override
    public CartDto viewCartByCustomer() {
        Customer loggedInCustomer = customerService.getCurrentlyLoggedInUser();
        Cart cart = cartRepository.findByCustomer(loggedInCustomer);
        if (cart.getItems().isEmpty())
            throw new ResourceNotFoundException("Your Cart is empty!");

        double cartTotal = cart.getTotal();
        BigDecimal bdCartTotal = new BigDecimal(cartTotal);
        bdCartTotal = bdCartTotal.setScale(2, RoundingMode.HALF_UP);

        List<Item> cartItems = new ArrayList(cart.getItems());

        Collections.sort(cartItems, Comparator.comparing(Item::getId, Comparator.reverseOrder()));
        return CartDto.builder()
                .items(cartItems)
                .total(bdCartTotal).build();
    }

    @Override
    public List<CartItemResponseDto> fetchProductsFromCustomerCart() {
        Customer loggedInCustomer = customerService.getCurrentlyLoggedInUser();
        Cart cart = loggedInCustomer.getCart();
        Set<Item> items = cart.getItems();
        List<CartItemResponseDto> itemDtos = new ArrayList<>();
        for (Item item : items) {
            CartItemResponseDto itemDto = new CartItemResponseDto();
            BeanUtils.copyProperties(item, itemDto);
            itemDtos.add(itemDto);
        }
        return itemDtos;
    }

}
