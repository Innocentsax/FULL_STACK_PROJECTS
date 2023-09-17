package com.decagon.safariwebstore.service.serviceImplementation;

import com.decagon.safariwebstore.dto.OrderRequestDTO;
import com.decagon.safariwebstore.dto.OrderResponseDTO;
import com.decagon.safariwebstore.dto.UserDTO;
import com.decagon.safariwebstore.exceptions.ResourceNotFoundException;
import com.decagon.safariwebstore.model.*;
import com.decagon.safariwebstore.repository.*;
import com.decagon.safariwebstore.service.AddressService;
import com.decagon.safariwebstore.service.CheckOutService;
import com.decagon.safariwebstore.service.OrderService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor

public class CheckOutServiceImplementation implements CheckOutService {

    private final UserRepository userRepository;

    private final OrderService orderService;

    private final OrderedItemRepository orderedItemRepository;

    private final AddressService addressService;

    private final AddressRepository addressRepository;

    private final CartItemRepository cartItemRepository;

    private final ShippingAddressRepository shippingAddressRepository;

    private final ModelMapper modelMapper;


    @Override
    public OrderResponseDTO doCheckout(OrderRequestDTO orderRequestDTO, String email) {
        User currentUser = getCurrentUserByEmail(email);

        ShippingAddress shippingAddress = setDefaultShippingAddress(orderRequestDTO.getShippingAddress(), currentUser);
        orderRequestDTO.setStatus(orderRequestDTO.getStatus().toUpperCase());
        List<OrderedItem> orderedItemList = getOrderedItems(orderRequestDTO.getCartItemIds(), currentUser);
        Order currentOrder = modelMapper.map(orderRequestDTO, Order.class);
        currentOrder.setUser(currentUser);
        orderedItemRepository.saveAll(orderedItemList);
        currentOrder.setOrderedItems(orderedItemList);
        currentOrder.setShippingAddress(shippingAddress);
        orderService.saveOrder(currentOrder);

        clearCartItems(orderRequestDTO.getCartItemIds());


        OrderResponseDTO orderResponseDTO = modelMapper.map(currentOrder, OrderResponseDTO.class);
        UserDTO userDTO = modelMapper.map(currentUser, UserDTO.class);
        orderResponseDTO.setOrderedBy(userDTO);
        return orderResponseDTO;
    }

    private void clearCartItems(List<Long> cartItemIds) {
        cartItemRepository.deleteAllById(cartItemIds);
    }

    private ShippingAddress setDefaultShippingAddress(ShippingAddress currentShippingAddress, User currentUser) {
        Boolean userDefaultAddressExists = addressService.userDefaultAddressExists(currentUser);
        Boolean addressExists = addressRepository
                .existsAddressByUserAndAddressAndCityAndState(currentUser,
                        currentShippingAddress.getAddress(), currentShippingAddress.getCity(),
                        currentShippingAddress.getState());

        if (currentShippingAddress.getIsDefaultShippingAddress()
                && addressExists) {
            Address savedShippingAddress = addressService
                    .getAddressByUserAndAddressAndCityAndState(currentUser,
                            currentShippingAddress.getAddress(), currentShippingAddress.getCity(),
                            currentShippingAddress.getState());
            savedShippingAddress.setIsDefaultShippingAddress(true);
            addressRepository.save(savedShippingAddress);

            if (userDefaultAddressExists && !currentShippingAddress.getIsDefaultShippingAddress()) {
                Address userDefaultAddress = addressService.getUserDefaultAddress(currentUser);
                userDefaultAddress.setIsDefaultShippingAddress(false);
                addressRepository.save(userDefaultAddress);
            }else{
                Address userDefaultAddress = addressService.getUserDefaultAddress(currentUser);
                userDefaultAddress.setIsDefaultShippingAddress(true);
                addressRepository.save(userDefaultAddress);

            }
        } else if ((!currentShippingAddress.getIsDefaultShippingAddress() && !addressExists)) {
            Address newAddress = modelMapper.map(currentShippingAddress, Address.class);
            newAddress.setUser(currentUser);
            addressRepository.save(newAddress);
        } else if (currentShippingAddress.getIsDefaultShippingAddress()
                && !addressExists) {
            Address newAddress = modelMapper.map(currentShippingAddress, Address.class);
            newAddress.setUser(currentUser);
            newAddress.setIsDefaultShippingAddress(true);
            addressRepository.save(newAddress);
        }

        shippingAddressRepository.save(currentShippingAddress);

        return currentShippingAddress;
    }

    private User getCurrentUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> {
                    throw new ResourceNotFoundException("User not found!");
                }
        );
    }

    private List<OrderedItem> getOrderedItems(List<Long> cartItemIds, User currentUser) {
        List<CartItem> cartItemList = getCartItemsFromIds(cartItemIds, currentUser);

       return cartItemList.stream().map(cartItem -> {
           OrderedItem orderedItem = new OrderedItem();
           orderedItem.setProductId(cartItem.getProductId());
           orderedItem.setQuantity(cartItem.getQuantity());
           orderedItem.setPrice(cartItem.getPrice());
           orderedItem.setName(cartItem.getName());
           orderedItem.setDescription(cartItem.getDescription());
           orderedItem.setSize(cartItem.getSize());
           orderedItem.setColor(cartItem.getColor());
           orderedItem.setProductImage(cartItem.getProductImage());
           return orderedItem;
       }).collect(Collectors.toList());
    }

    private List<CartItem> getCartItemsFromIds(List<Long> cartItemIds, User currentUser) {
        return cartItemIds.stream().map(cartItemId -> cartItemRepository
                .findByUserAndId(currentUser, cartItemId)
                .orElseThrow(() -> {
                    throw new ResourceNotFoundException("Cart item not found!");
                })).collect(Collectors.toList());
    }

}
