package com.example.cedarxpressliveprojectjava010.service.impl;

import com.example.cedarxpressliveprojectjava010.dto.CheckOutDto;
import com.example.cedarxpressliveprojectjava010.dto.OrderDto;
import com.example.cedarxpressliveprojectjava010.entity.*;
import com.example.cedarxpressliveprojectjava010.enums.DeliveryStatus;
import com.example.cedarxpressliveprojectjava010.exception.CartEmptyException;
import com.example.cedarxpressliveprojectjava010.repository.*;
import com.example.cedarxpressliveprojectjava010.service.CheckOutService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class CheckOutServiceImpl implements CheckOutService {

    private OrderRepository orderRepository;
    private OrderItemRepository orderItemRepository;
    private UserRepository userRepository;
    private AddressRepository addressRepository;
    private CartRepository cartRepository;



    @Override
    public ResponseEntity<OrderDto> makeOrder(CheckOutDto checkOutDto) {
        String loggedInEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.getUserByEmail(loggedInEmail);
        Cart cart = cartRepository.findCartByCustomer(user).get();
        if(cart.getCartItems().size() < 1) throw new CartEmptyException("Cart is Empty");
        Address address = addressRepository.getById(checkOutDto.getAddress_id());

        Order order = Order.builder().customer(user)
                .customerOrder(new ArrayList<>())
                .customer(user)
                .deliveryFee(0.00)
                .paymentMethod(checkOutDto.getPaymentMethod())
                .discount(0.00)
                .deliveryStatus(DeliveryStatus.PENDING)
                .price(0D)
                .address(address)
                .build();

        order = orderRepository.save(order);
        List<OrderItem> orderItems = this.convertCartToOrderItemList(cart,order);
        order.setCustomerOrder(orderItems);
        order.setPrice(cart.getCost());
        OrderDto orderDto = this.mapToDto(orderRepository.save(order));
        return ResponseEntity.ok(orderDto);

    }

    private List<OrderItem> convertCartToOrderItemList(Cart cart,Order order){
        List<OrderItem> orderItems = new ArrayList<>();

        for(CartItem each: cart.getCartItems()){
            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(each.getProduct())
                    .unit(each.getUnit())
                    .build();

            orderItemRepository.save(orderItem);
            orderItems.add(orderItem);
        }
        return orderItems;
    }

    private OrderDto mapToDto(Order order){
        return OrderDto.builder()
                .deliveryStatus(order.getDeliveryStatus())
                .address(order.getAddress().getAddress())
                .paymentMethod(order.getPaymentMethod())
                .user(order.getCustomer())
                .price(order.getPrice())
                .customerOrder(order.getCustomerOrder())
                .timeOfCompletion(null)
                .build();
    }
}
