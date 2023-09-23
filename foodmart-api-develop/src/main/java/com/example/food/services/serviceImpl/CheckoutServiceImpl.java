package com.example.food.services.serviceImpl;

import com.example.food.Enum.OrderStatus;
import com.example.food.Enum.PaymentMethod;
import com.example.food.Enum.ResponseCodeEnum;
import com.example.food.dto.CartItemDto;
import com.example.food.dto.CheckoutDto;
import com.example.food.dto.UpdateProductDto;
import com.example.food.model.*;
import com.example.food.pojos.OrderResponse;
import com.example.food.repositories.*;
import com.example.food.services.CheckoutService;
import com.example.food.services.ProductService;
import com.example.food.util.ResponseCodeUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CheckoutServiceImpl implements CheckoutService {
    private final WalletRepository walletRepository;
    private final AddressRepository addressRepository;
    private final OrderedItemRepository orderedItemRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final CartServiceImpl cartService;
    private final ProductService productService;
    private final ResponseCodeUtil responseCodeUtil = new ResponseCodeUtil();

    private Users getLoggedInUser() {
        String authentication = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(authentication)
                .orElseThrow(() -> new RuntimeException("User not authorized"));
    }

    @Override
    public OrderResponse checkout(CheckoutDto checkoutDto) {
        try {
            Users user = getLoggedInUser();
            Cart userCart = cartRepository.findByUsersEmail(user.getEmail()).orElseThrow(RuntimeException::new);

            if (userCart.getCartItemList().isEmpty())
                throw new RuntimeException("Cart is empty, Please add product to cart");

            BigDecimal deliveryFee = checkoutDto.getDeliveryMethod().getFee();
            BigDecimal cartTotal = userCart.getCartTotal();
            BigDecimal discount = userCart.getCartTotal().compareTo(BigDecimal.valueOf(10_000)) >= 0 ? (cartTotal.multiply(BigDecimal.valueOf(0.05))) : BigDecimal.valueOf(0);
            BigDecimal orderTotal = deliveryFee.add(cartTotal.subtract(discount));

            if (user.getWallet().getWalletBalance().compareTo(orderTotal) < 0)
                return responseCodeUtil.updateResponseData(new OrderResponse(), ResponseCodeEnum.ERROR, "Insufficient balance Please fund your wallet");

            Wallet wallet = user.getWallet();
            wallet.setWalletBalance(wallet.getWalletBalance().subtract(orderTotal));
            walletRepository.save(wallet);

            Address orderAddress = Address.builder()
                    .streetAddress(checkoutDto.getStreetAddress())
                    .city(checkoutDto.getCity())
                    .state(checkoutDto.getState())
                    .build();
            addressRepository.save(orderAddress);

            List<Address> usersAddresses = user.getAddress();
            usersAddresses.add(orderAddress);
            userRepository.save(user);

            List<OrderedItem> orderedItemList = new ArrayList<>();
            for (CartItem item : userCart.getCartItemList()) {
                OrderedItem orderedItem = OrderedItem.builder()
                        .product(item.getProduct())
                        .quantity(item.getQuantity())
                        .subTotal(item.getSubTotal())
                        .build();
                orderedItemList.add(orderedItem);
                orderedItemRepository.save(orderedItem);
            }

            for(CartItem item : userCart.getCartItemList()){
                UpdateProductDto updateProduct = new UpdateProductDto(item.getProduct().getProductName(), item.getProduct().getQuantity() - item.getQuantity(), item.getProduct().getProductPrice());
                productService.updateProduct(item.getProduct().getId(), updateProduct);
            }

            Order order = Order.builder()
                    .imageUrl("image")
                    .createdAt(new Date())
                    .quantity(userCart.getCartItemList().size())
                    .orderStatus(OrderStatus.PENDING)
                    .user(user)
                    .orderedItem(orderedItemList)
                    .address(orderAddress)
                    .paymentMethod(PaymentMethod.WALLET)
                    .deliveryMethod(checkoutDto.getDeliveryMethod())
                    .deliveryFee(deliveryFee)
                    .discount(discount)
                    .subTotal(cartTotal)
                    .totalOrderPrice(orderTotal)
                    .build();
            Order savedOrder = orderRepository.save(order);
            List<CartItemDto> cartItemDtos = new ArrayList<>();
            CartItemDto cartItemDto;

            for (CartItem item : userCart.getCartItemList()) {
                ObjectMapper objectMapper = new ObjectMapper();
                cartItemDto = objectMapper.convertValue(item, CartItemDto.class);
                cartItemDtos.add(cartItemDto);
            }

            OrderResponse response = OrderResponse.builder()
                    .id(savedOrder.getId())
                    .imageUrl(savedOrder.getImageUrl())
                    .createdAt(savedOrder.getCreatedAt())
                    .modifiedAt(null)
                    .quantity(savedOrder.getQuantity())
                    .orderStatus(savedOrder.getOrderStatus())
                    .cartItemDtoList(cartItemDtos)
                    .address(savedOrder.getAddress())
                    .paymentMethod(savedOrder.getPaymentMethod())
                    .deliveryFee(savedOrder.getDeliveryFee())
                    .discount(discount)
                    .deliveryMethod(savedOrder.getDeliveryMethod())
                    .totalOrderPrice(savedOrder.getTotalOrderPrice())
                    .build();
            response.setCode(0);

            cartService.clearCart();

            return responseCodeUtil.updateResponseData(response, ResponseCodeEnum.SUCCESS, "Thanks for shopping, Your Order has been placed");
        } catch (Exception e) {
            log.error("Empty checkout: {}", e.getMessage());
            return responseCodeUtil.updateResponseData(new OrderResponse(), ResponseCodeEnum.ERROR);
        }
    }
}